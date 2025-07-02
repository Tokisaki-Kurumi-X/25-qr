package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Record.RecordDao;
import com.example.unity_backend.Entity.GameRecord;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Service
public class RecordService {
    private RecordDao recordDao;
    private JSONObject res;
    private Authentication authentication;
    private String JWTusername;

    @Autowired
    public RecordService(RecordDao recordDao1,HttpServletRequest request1){
        this.recordDao=recordDao1;
        res=new JSONObject();
        authentication = SecurityContextHolder.getContext().getAuthentication();
    }
    private void getJWTUsername(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        this.JWTusername=authentication.getName();
    }

    public JSONObject newRecord(GameRecord gameRecord) throws IOException {
        res.clear();
        //init
        getJWTUsername();//Filter已做校验，不会空
        gameRecord.setUserName(JWTusername);

        //LogUtil.showDebug(gameRecord.toString());
        //new

        //LogUtil.showDebug(String.valueOf(gameRecord.getRecordID()));
        //update
        float maxHistory=recordDao.getMaxGradeRecord(gameRecord.getUserName());
       // LogUtil.showDebug("max is "+maxHistory);
        boolean shouldUpdate=false;
        if(gameRecord.getGrade()>maxHistory){
            shouldUpdate=true;
            res.put("isMax","true");
        }else {
            res.put("isMax","false");
        }
        recordDao.newGameRecord(gameRecord);
        if(shouldUpdate){
            recordDao.updateMaxRecord(gameRecord);
        }
        res.put("res_msg","success");
        return res;
    }

    public JSONObject getAllRecords() throws IOException {
        res.clear();
        getJWTUsername();
        List<GameRecord> records=recordDao.getAllGameRecords(JWTusername);
        res.put("aaData",records);
        res.put("type","normal");
        return res;
    }

    public JSONObject getMaxRecords() throws IOException {
        res.clear();
        getJWTUsername();
        List<GameRecord> records=recordDao.getMaxHistoryRecords(JWTusername);
        res.put("aaData",records);
        res.put("type","max");
        return res;
    }

    public JSONObject getMaxGrade() throws IOException {
        res.clear();
        getJWTUsername();
        float maxHistory=recordDao.getMaxGradeRecord(JWTusername);
        res.put("maxGrade",String.valueOf(maxHistory));
        return res;
    }
}
