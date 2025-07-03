package com.example.unity_backend.Dao.Log;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Dao.Record.RecordMapper;
import com.example.unity_backend.Entity.BalanceLog;
import com.example.unity_backend.Entity.ItemLog;
import com.example.unity_backend.Entity.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogDao {
    private Mybatis mybatis;
    private LogMapper logMapper;

    @Autowired
    public LogDao(Mybatis mybatis1){
        this.mybatis=mybatis1;
    }

    private void openDB() throws IOException {
        this.mybatis.initMybatis();
        this.logMapper=mybatis.getSqlSession().getMapper(LogMapper.class);
    }
    private void closeDB(){
        this.mybatis.closeSqlSession();
    }

    public void newLoginLog(LoginLog loginLog){

    }

    public void newItemLog(ItemLog itemLog){

    }

    public void newBalanceLog(BalanceLog balanceLog){

    }
}
