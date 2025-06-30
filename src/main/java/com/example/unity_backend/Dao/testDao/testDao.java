package com.example.unity_backend.Dao.testDao;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Db.Db;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import com.example.unity_backend.Utils.SqlUtils.SqlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
@Profile("test")
@Component
public class testDao {
    private Db db;
    private SqlGenerator sqlGenerator;
    @Autowired
    public testDao(Db DB,SqlGenerator sql){
        this.db=DB;
        this.sqlGenerator=sql;
    }
    public JSONObject testPath() throws SQLException, ClassNotFoundException {
        String sql=sqlGenerator.generatetestPath();
        //LogUtil.showDebug(sql);
        return db.testPath(sql);
    }
    public void testQuery(String sql) throws SQLException, ClassNotFoundException {
        LogUtil.showDebug(db.excuteDBquery(sql).toString());
    }
}
