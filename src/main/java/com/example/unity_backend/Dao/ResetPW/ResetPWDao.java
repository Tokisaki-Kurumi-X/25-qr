package com.example.unity_backend.Dao.ResetPW;

import com.example.unity_backend.Dao.Log.LogMapper;
import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Dao.UserInfo.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResetPWDao {
    private Mybatis mybatis;
    private ResetPWMapper resetPWMapper;

    @Autowired
    public ResetPWDao(Mybatis mybatis1){
        this.mybatis=mybatis1;
    }
    private void openDB() throws IOException {
        this.mybatis.initMybatis();
        this.resetPWMapper=mybatis.getSqlSession().getMapper(ResetPWMapper.class);
    }
    private void closeDB(){
        this.mybatis.closeSqlSession();
    }

    public void resetPassword(String username,String password) throws IOException {
        openDB();
        resetPWMapper.resetPassword(username,password);
        closeDB();
    }
}
