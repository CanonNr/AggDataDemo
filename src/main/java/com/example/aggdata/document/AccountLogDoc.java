package com.example.aggdata.document;

import com.example.aggdata.entity.BrowserData;
import com.example.aggdata.entity.RequestData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "account_log")
public class AccountLogDoc {

    @Id
    private Long id;

    private Long tenantId;

    private Long userId;

    private String phone;

    private String nickname;

    private Byte type;

    private Byte status;

    private BrowserData browserData;

    private RequestData requestData;

    private String errorMessage;

    private String ip;

    private String urlFrom;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;


}
