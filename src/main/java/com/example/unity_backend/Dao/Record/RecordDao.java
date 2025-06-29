package com.example.unity_backend.Dao.Record;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Dao.User.Login.LoginMapper;
import com.example.unity_backend.Entity.GameRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RecordDao {
    private Mybatis mybatis;
    private RecordMapper recordMapper;
    private GameRecord gameRecord;

    @Autowired
    public RecordDao(Mybatis mybatis1){
        this.mybatis=mybatis1;
    }

    private void openDB() throws IOException {
        this.mybatis.initMybatis();
        this.recordMapper=mybatis.getSqlSession().getMapper(RecordMapper.class);
    }
    private void closeDB(){
        this.mybatis.closeSqlSession();
    }

    public GameRecord getAllGameRecord(String username) throws IOException {
        openDB();

        //recordMapper.getAllGameRecord(username);
        //需要封装成list
        closeDB();
        return null;
    }

    public void newGameRecord(GameRecord gameRecord){
        
    }
}
