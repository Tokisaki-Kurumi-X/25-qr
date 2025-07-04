package com.example.unity_backend.Dao.Log;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Dao.Record.RecordMapper;
import com.example.unity_backend.Entity.BalanceLog;
import com.example.unity_backend.Entity.ItemLog;
import com.example.unity_backend.Entity.ItemLogVO;
import com.example.unity_backend.Entity.LoginLog;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
//读写，更删
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

    @CacheEvict(value = "loginLog",key = "#loginLog.Username")
    public void newLoginLog(LoginLog loginLog) throws IOException {
        openDB();
        logMapper.newLoginLog(loginLog);
        closeDB();
    }

    @CacheEvict(value = "itemLog",key = "#itemLog.Username")
    public void newItemLog(ItemLog itemLog) throws IOException {
        openDB();
        logMapper.newItemLog(itemLog);
        closeDB();
    }

    @CacheEvict(value = "balanceLog",key = "#balanceLog.Username")
    public void newBalanceLog(BalanceLog balanceLog) throws IOException {
        openDB();
        logMapper.newBalanceLog(balanceLog);
        closeDB();
    }

    @Cacheable(value = "balanceLog",key = "#username")
    public List<BalanceLog> getBalanceLog(String username) throws IOException {

        openDB();
        List<BalanceLog> balanceLogs=logMapper.getBalanceLog(username);
        closeDB();
        return balanceLogs;
    }

    @Cacheable(value = "itemLog",key = "#username")
    public List<ItemLogVO> getItemLog(String username) throws IOException {
        openDB();
        List<ItemLogVO> itemLogs=logMapper.getItemLog(username);
        closeDB();
        return itemLogs;
    }

    @Cacheable(value = "loginLog",key = "#username")
    public List<LoginLog> getLoginLog(String username) throws IOException {
        openDB();
        List<LoginLog> loginLogs=logMapper.getLoginLog(username);
        closeDB();
        return loginLogs;
    }
}
