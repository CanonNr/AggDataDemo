package com.example.aggdata.service.impl;

import com.example.aggdata.document.AccountLogDoc;
import com.example.aggdata.repository.AccountLogRepository;
import com.example.aggdata.service.AccountLogService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountLogImpl implements AccountLogService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Resource
    private AccountLogRepository accountLogRepository;

    @Resource
    public ElasticsearchOperations elasticsearchOperations;

    @Override
    public ArrayList<AccountLogDoc> findAll() {
        ArrayList<AccountLogDoc> all = new ArrayList<>();
        accountLogRepository.findAll().iterator().forEachRemaining(all::add);
        return all;
    }

    @Override
    public ArrayList<AccountLogDoc> getByBrowser(String browser) {
        return null;
    }

//    public ArrayList<AccountLogDoc> getByBrowser(String browser) {
////        TermQueryBuilder chrome = QueryBuilders.termQuery("body.browser.keyword", browser);
////        ArrayList<AccountLogDoc> list = new ArrayList<>();
//////        accountLogRepository.search(chrome).iterator().forEachRemaining(list::add);
////        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
////        nativeSearchQueryBuilder.withQuery(chrome);
////        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
////
////        SearchHits<List> accountLog = elasticsearchOperations.search(nativeSearchQuery, List.class, IndexCoordinates.of("account_log"/*索引名*/));
////        accountLog.iterator().forEachRemaining(System.out::println);
////        return list;
//    }
}
