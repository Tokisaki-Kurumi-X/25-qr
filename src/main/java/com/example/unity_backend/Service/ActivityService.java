package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Activity.ActivityDao;
import com.example.unity_backend.Entity.ActivityVO;
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
}
