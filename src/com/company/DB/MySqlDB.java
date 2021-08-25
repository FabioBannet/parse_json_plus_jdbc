package com.company.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Function;

public class MySqlDB implements IMySqlDB{
    private String url;
    private String userName;
    private String password;

    {
        url = "jdbc:mysql://localhost:3306";
        userName = "root";
        password = "root";
    }

    public MySqlDB() {}

    public MySqlDB(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public void executeUpdateSQL(String sql){
        try(Connection conn = DriverManager.getConnection(url, userName, password)){

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Object executeQuery(String sql, Function func){

        try(Connection conn = DriverManager.getConnection(url, userName, password)){

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet res = statement.executeQuery();

            return func.apply(res);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
