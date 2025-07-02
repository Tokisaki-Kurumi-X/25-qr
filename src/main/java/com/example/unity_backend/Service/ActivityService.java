package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Activity.ActivityDao;
import com.example.unity_backend.Entity.Activity;
import com.example.unity_backend.Entity.ActivityVO;
import com.example.unity_backend.Entity.ParticipateDTO;
import com.example.unity_backend.Entity.Participation;
import com.example.unity_backend.Utils.JWTUtils.JWTUtil;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ActivityService {
    private JSONObject res;
    private ActivityDao activityDao;
    private Authentication authentication;
    private String JWTusername;
    @Autowired
    public ActivityService(ActivityDao activityDao1){
        this.activityDao=activityDao1;
        this.res=new JSONObject();
    }

    private void getJWTUsername(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        this.JWTusername=authentication.getName();
    }


    public JSONObject getRewardAndList() throws IOException {
        res.clear();
        getJWTUsername();
        List<ActivityVO> activityVOList=activityDao.getListandReword(JWTusername);
        //get
        res.put("aaData",activityVOList);
        return res;
    }

    public JSONObject Participate(Activity activity) throws IOException {
        res.clear();
        getJWTUsername();
        ParticipateDTO participateDTO=new ParticipateDTO();
        participateDTO.setActivityID(activity.getActivityID());
        participateDTO.setUserName(JWTusername);
        activityDao.participate(participateDTO);
        res.put("status","success");
        return res;
    }

    //change redis
    public JSONObject getReward(Activity activity) throws IOException {
        res.clear();
        getJWTUsername();
        ParticipateDTO participateDTO=new ParticipateDTO();
        participateDTO.setActivityID(activity.getActivityID());
        participateDTO.setUserName(JWTusername);
        activityDao.getReward(participateDTO);
        res.put("status","success");
        return res;
    }

    //change redis
    public JSONObject updateProgress(ActivityVO activityVO) throws IOException {
        res.clear();
        getJWTUsername();
        Participation participation=new Participation();
        participation.setActivityID(activityVO.getActivityID());
        participation.setUserName(JWTusername);
        participation.setCurrentProgress(activityVO.getUserCurrentProgress());
        //LogUtil.showDebug(participation.toString());
        activityDao.updateUserProgress(participation);
        res.put("status","success");
        return res;
    }
}
