package com.example.aggdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.aggdata.document.AccountLogDoc;
import com.example.aggdata.entity.AccountLog;
import com.example.aggdata.entity.BrowserData;
import com.example.aggdata.message.AccountLogMessage;
import com.example.aggdata.service.AccountLogService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class AggDataDemoApplicationTests {

    @Resource
    private ElasticsearchRestTemplate esRestTemplate;

    @Resource
    private AccountLogService accountLogService;

    @Resource
    public ElasticsearchOperations elasticsearchOperations;

    @Test
    void contextLoads() {
        String json = "{\"data\":[{\"id\":\"31\",\"tenant_id\":\"10\",\"user_id\":\"15\",\"phone\":null,\"nickname\":null,\"type\":null,\"status\":null,\"error_message\":null,\"ip\":null,\"body\":\"{\\\"device\\\": \\\"WebKit\\\", \\\"browser\\\": \\\"Chrome\\\", \\\"platform\\\": \\\"Windows\\\"}\",\"url_from\":null,\"created_at\":null,\"updated_at\":null,\"deleted_at\":null}],\"database\":\"ultra_account\",\"es\":1624247254000,\"id\":2,\"isDdl\":false,\"mysqlType\":{\"id\":\"int(11)\",\"tenant_id\":\"int(11)\",\"user_id\":\"int(11)\",\"phone\":\"char(11)\",\"nickname\":\"varchar(16)\",\"type\":\"bit(1)\",\"status\":\"bit(1)\",\"error_message\":\"varchar(64)\",\"ip\":\"varchar(15)\",\"body\":\"json\",\"url_from\":\"varchar(255)\",\"created_at\":\"datetime\",\"updated_at\":\"datetime\",\"deleted_at\":\"datetime\"},\"old\":null,\"pkNames\":[\"id\"],\"sql\":\"\",\"sqlType\":{\"id\":4,\"tenant_id\":4,\"user_id\":4,\"phone\":1,\"nickname\":12,\"type\":-7,\"status\":-7,\"error_message\":12,\"ip\":12,\"body\":12,\"url_from\":12,\"created_at\":93,\"updated_at\":93,\"deleted_at\":93},\"table\":\"account_log\",\"ts\":1624247254460,\"type\":\"INSERT\"}";
        AccountLogMessage accountLogMessage = JSON.parseObject(json, AccountLogMessage.class);

        if (!accountLogMessage.isDdl
                && accountLogMessage.database.equals("ultra_account")
                && accountLogMessage.table.equals("account_log")){
            for (AccountLog log:accountLogMessage.data) {
//                AccountLogDoc doc = new AccountLogDoc();
//                doc.id = log.getId();
//                doc.tenantId = log.getTenantId();
//                doc.browserData = JSON.parseObject(log.getBrowserData(), BrowserData.class);
//                esRestTemplate.save(doc);
            }
        }
    }

    @Test
    void getAll() {
        ArrayList<AccountLogDoc> all = accountLogService.findAll();
        System.out.println("----------");
        System.out.println(all);
        String o = JSON.toJSONString(all, SerializerFeature.WriteMapNullValue);
        System.out.println(o);
        System.out.println("----------");
    }

    @Test
    void getByBrowser() {
        ArrayList<AccountLogDoc> all = accountLogService.getByBrowser("Chrome");
        System.out.println("----------");
        System.out.println(all);
        String o = JSON.toJSONString(all, SerializerFeature.WriteMapNullValue);
        System.out.println(o);
        System.out.println("----------");
    }

    @Test
    void test1(){
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 查询必须满足的条件
        boolQueryBuilder.must(QueryBuilders.termQuery("browserData.browser.keyword", "Chrome"));
        boolQueryBuilder.must(QueryBuilders.termQuery("tenantId", 15));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        SearchHits<AccountLogDoc> result = this.elasticsearchOperations.search(nativeSearchQuery, AccountLogDoc.class, IndexCoordinates.of("account_log"));
        List<SearchHit<AccountLogDoc>> searchHits = result.getSearchHits();
        List<AccountLogDoc> list = new LinkedList<>();
        searchHits.forEach(ele->list.add(ele.getContent()));
        System.out.println(list);
        String o = JSON.toJSONString(list,SerializerFeature.WriteMapNullValue);
        System.out.println(o);
    }

}
