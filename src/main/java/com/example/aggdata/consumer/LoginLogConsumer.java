package com.example.aggdata.consumer;

import com.alibaba.fastjson.JSON;
import com.example.aggdata.document.AccountLogDoc;
import com.example.aggdata.entity.AccountLog;
import com.example.aggdata.entity.BrowserData;
import com.example.aggdata.entity.RequestData;
import com.example.aggdata.message.AccountLogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class LoginLogConsumer {

    @Resource
    private ElasticsearchRestTemplate esRestTemplate;

    @KafkaListener(
            topicPartitions = {
                    @TopicPartition(topic = "ultra_account_account_log",partitions = { "0" })
            }
    )
    public void onMessage(String message) throws InterruptedException {
        AccountLogMessage accountLogMessage = JSON.parseObject(message, AccountLogMessage.class);
        // 判断是否为非DDL操作，且库名表明符合
        if (!accountLogMessage.isDdl
                && accountLogMessage.database.equals("ultra_account")
                && accountLogMessage.table.equals("account_log")){
            for (AccountLog log:accountLogMessage.data) {
                AccountLogDoc doc = new AccountLogDoc();
                doc.setId(log.getId());
                doc.setTenantId(log.getTenantId());
                doc.setIp(log.getIp());
                doc.setType(log.getType());
                doc.setStatus(log.getStatus());
                doc.setErrorMessage(log.getErrorMessage());
                doc.setUrlFrom(log.getUrlFrom());
                doc.setCreatedAt(log.getCreatedAt());
                doc.setUpdatedAt(log.getUpdatedAt());
                doc.setDeletedAt(log.getDeletedAt());
                doc.setBrowserData(JSON.parseObject(log.getBrowserData(), BrowserData.class));
                doc.setRequestData(JSON.parseObject(log.getRequestData(), RequestData.class));
                esRestTemplate.save(doc);
            }
        }

    }
}
