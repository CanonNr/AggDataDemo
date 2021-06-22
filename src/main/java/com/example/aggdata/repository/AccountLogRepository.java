package com.example.aggdata.repository;

import com.example.aggdata.document.AccountLogDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface  AccountLogRepository extends  ElasticsearchRepository<AccountLogDoc, Long> {
}
