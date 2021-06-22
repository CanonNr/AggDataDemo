package com.example.aggdata.entity;

import lombok.Data;
import java.util.Date;

@Data
public class AccountLog {
    private Long id;

    /**
     * 租户id
     */
    private Long tenantId;

    private Integer userId;

    private Byte status;

    private Byte type;

    private String requestData;

    private String browserData;

    private String errorMessage;

    private String ip;

    private String urlFrom;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

}
