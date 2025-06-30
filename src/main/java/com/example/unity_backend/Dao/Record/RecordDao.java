package com.example.unity_backend.Dao.Record;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Dao.User.Login.LoginMapper;
import com.example.unity_backend.Entity.GameRecord;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

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

    public void newGameRecord(GameRecord gameRecord) throws IOException {
        openDB();
        gameRecord.setPlayTime(new Date(System.currentTimeMillis()));
        int id=recordMapper.newGameRecord(gameRecord);
        //LogUtil.showDebug("id is "+gameRecord.getRecordID());
        //gameRecord.setRecordID(id);
        closeDB();
    }

    public float getMaxGradeRecord(String username) throws IOException {
        openDB();
        float res=recordMapper.getMaxGrade(username);
        closeDB();
        return res;
    }

    public void updateMaxRecord(GameRecord gameRecord) throws IOException {
        openDB();
        recordMapper.setMaxRecordHistory(gameRecord);
        closeDB();
    }
}
