package com.example.unity_backend.Dao.Record;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Dao.User.Login.LoginMapper;
import com.example.unity_backend.Entity.GameRecord;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class RecordDao {
    private Mybatis mybatis;
    private RecordMapper recordMapper;
    private GameRecord gameRecord;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

    public List<GameRecord> getAllGameRecords(String username,String level) throws IOException {
        openDB();
        //recordMapper.getAllGameRecord(username);
        //需要封装成list
        List<GameRecord> res=recordMapper.getAllGameRecord(username,level);
        closeDB();
        return res;
    }

    public List<GameRecord> getMaxHistoryRecords(String username,String level) throws IOException {
        openDB();
        List<GameRecord> res=recordMapper.getAllMaxUpdateGameRecord(username,level);
        closeDB();
        return res;
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

    public float getMincostTime(String username,String level) throws IOException {
        openDB();
        float time=recordMapper.getMincostTime(username,level);
        closeDB();
        return  time;
    }

}
