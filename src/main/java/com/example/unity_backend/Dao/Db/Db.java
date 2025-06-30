package com.example.unity_backend.Dao.Db;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Entity.Db_connectionMeta;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//通用函数，所有数据库操作都在这个类
@Component
public class Db {
    private final Db_connectionMeta db_connectionMeta;
    private Statement statement;
    private Connection connection;
    @Autowired
    public Db(Db_connectionMeta db_connectionmeta){
        this.db_connectionMeta=db_connectionmeta;
    }

    //内部函数开始
    private void openDB() throws ClassNotFoundException, SQLException {
        //String url="jdbc:mysql://localhost:3306/"+Dbname+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String url=db_connectionMeta.getUrl()+db_connectionMeta.getOptions();
        String username=db_connectionMeta.getUsername();
        String password=db_connectionMeta.getPassword();
        Class.forName(db_connectionMeta.getDriver());
        connection= DriverManager.getConnection(url,username,password);
        statement=connection.createStatement();
    }
    private void closeDB() throws SQLException {
        statement.close();
        connection.close();
    }
    private JSONObject queryDB(String sql) throws SQLException {
        LogUtil.showDebug("now excute query:  "+"\""+sql+"\"");
        List list=new ArrayList();
        JSONObject DB_result=new JSONObject();
        ResultSet rs=statement.executeQuery(sql);
        ResultSetMetaData rsmd=rs.getMetaData();
        int fieldCount = rsmd.getColumnCount();
        while (rs.next()) {
            Map map = new HashMap();
            for (int i = 0; i < fieldCount; i++) {
                map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
            }
            list.add(map);
        }
        DB_result.put("aaData",list);
        rs.close();
        return  DB_result;
    }
    private  JSONObject updateDB(String sql) throws SQLException {
        LogUtil.showDebug("now  excute \" "+sql+" \"");
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        JSONObject res=new JSONObject();
        res.put("result_code",000);
        return  res;
    }
    //内部函数结束

    //外部函数开始
    public JSONObject testPath(String  sql) throws SQLException, ClassNotFoundException {
        openDB();
        JSONObject resJson=new JSONObject(queryDB(sql));
        closeDB();
        return  resJson;
    }
    public JSONObject excuteDBquery(String sql) throws SQLException, ClassNotFoundException {
        openDB();
        JSONObject resJson=new JSONObject(queryDB(sql));
        closeDB();
        return  resJson;
    }
    public JSONObject excuteDBupdate(String sql) throws SQLException, ClassNotFoundException {
        openDB();
        JSONObject json=new JSONObject(updateDB(sql));
        closeDB();
        return json;
    }
    public void testSQL(String sql) throws SQLException, ClassNotFoundException {
        openDB();
        JSONObject json=new JSONObject(updateDB(sql));
        closeDB();
    }
    //外部函数结束
}
