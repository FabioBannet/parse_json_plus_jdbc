package com.company.DB;

import java.util.function.Function;

public interface IMySqlDB{
    void executeUpdateSQL(String sql);
    Object executeQuery(String sql, Function func);
    String getUrl();
    void setUrl(String url);
    void setUserName(String userName);
    void setPassword(String password);

}
