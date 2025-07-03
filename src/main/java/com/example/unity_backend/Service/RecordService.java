package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Record.RecordDao;
import com.example.unity_backend.Entity.Activity;
import com.example.unity_backend.Entity.GameRecord;
import com.example.unity_backend.Entity.Participation;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class RecordService {
    private RecordDao recordDao;
    private JSONObject res;
    private Authentication authentication;
    private String JWTusername;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private HttpServletRequest httpServletRequest;

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

    //redis remove
    public JSONObject newRecord(GameRecord gameRecord) throws IOException {
        res.clear();
        //init
        getJWTUsername();//Filter已做校验，不会空
        gameRecord.setUserName(JWTusername);
        float minTime=recordDao.getMincostTime(gameRecord.getUserName(),String.valueOf(gameRecord.getLevel()));
        LogUtil.showDebug(String.valueOf(minTime));
       // LogUtil.showDebug("max is "+maxHistory);
        boolean shouldUpdate=false;
        if(gameRecord.getCostTime()<minTime){
            shouldUpdate=true;
            res.put("isMax",true);
        }else {
            res.put("isMax",false);
        }
        gameRecord.setPlayTime((new Date(System.currentTimeMillis())));
        recordDao.newGameRecord(gameRecord);
        if(shouldUpdate||minTime==-1){
            recordDao.updateMaxRecord(gameRecord);
        }
        //更新currentprogress
        //获取参与列表
       List<Participation> participations=activityService.getParticipationByUsername(gameRecord.getUserName());
        for(Participation participation:participations){
            LogUtil.showDebug(participation.toString());
            //需要比较是否满足活动关卡
            Activity activity=activityService.getSingleActivityByID(participation.getActivityID());
            if(activity.getLevel().equals(String.valueOf(gameRecord.getLevel()))){
                int before=Integer.valueOf(participation.getCurrentProgress());
                int after=before+1;
                participation.setCurrentProgress(String.valueOf(after));
                activityService.updateUserProgress(participation);
            }

        }
        res.put("res_msg","success");
        redisTemplate.delete("activities:"+gameRecord.getUserName());
        return res;
    }

    //redis store
    public JSONObject getAllRecords(String level) throws IOException {
        res.clear();
        getJWTUsername();
        List<GameRecord> records=recordDao.getAllGameRecords(JWTusername,level);
        res.put("aaData",records);
        res.put("type","normal");
        return res;
    }

    //redis store
    public JSONObject getMaxRecords(String level) throws IOException {
        res.clear();
        getJWTUsername();
        List<GameRecord> records=recordDao.getMaxHistoryRecords(JWTusername,level);
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
