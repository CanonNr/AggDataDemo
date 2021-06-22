package com.example.aggdata.message;

import com.example.aggdata.entity.AccountLog;
import lombok.Data;
import java.util.List;

@Data
public class AccountLogMessage {
    public List<AccountLog> data;
    public String database;
    public String table;
    public String type;
    public Boolean isDdl;
}
