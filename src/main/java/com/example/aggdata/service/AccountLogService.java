package com.example.aggdata.service;

import com.example.aggdata.document.AccountLogDoc;

import java.util.ArrayList;

public interface AccountLogService {
    ArrayList<AccountLogDoc> findAll();

    ArrayList<AccountLogDoc> getByBrowser(String browser);
}
