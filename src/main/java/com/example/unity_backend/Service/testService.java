package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.testDao.testDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class testService {
    private final testDao testdao;

    @Autowired
    public testService(testDao testdao){
        this.testdao=testdao;
    }

    public JSONObject testPath() throws SQLException, ClassNotFoundException {
        return testdao.testPath();
    }
}
