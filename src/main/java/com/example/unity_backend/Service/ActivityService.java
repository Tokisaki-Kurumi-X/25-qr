package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Activity.ActivityDao;
import com.example.unity_backend.Dao.UserInfo.UserInfoDao;
import com.example.unity_backend.Entity.*;
import com.example.unity_backend.Utils.JWTUtils.JWTUtil;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    private LogService logService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    public ActivityService(ActivityDao activityDao1,LogService logService1){
        this.activityDao=activityDao1;
        this.res=new JSONObject();
        this.logService=logService1;
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
        //前端已判断满足条件
        ParticipateDTO participateDTO=new ParticipateDTO();
        participateDTO.setActivityID(activity.getActivityID());
        participateDTO.setUserName(JWTusername);
        activityDao.getReward(participateDTO);
        //写仓库
        //1.获取奖励列表
        List<Reword> rewords=activityDao.getRewardListByActID(activity.getActivityID());
        //LogUtil.showDebug(rewords.toString());
        //2.加入仓库
        ItemLog itemLog=new ItemLog();

        for(Reword reword:rewords){
            //判断是否为余额，余额走用户信息路线
            //LogUtil.showDebug(reword.getItemID());
            if(reword.getItemID().equals("999")){
                JSONObject balance=userInfoService.getUserBalance();
                Double Balance=Double.valueOf(balance.getString("balance"));//before
                Double after=Balance+Double.valueOf(reword.getItemNum());
                userInfoService.updateUserBalance(JWTusername,String.valueOf(after));
                //写余额日志
                BalanceLog balanceLog=new BalanceLog();
                balanceLog.setChangeType("活动领取");
                balanceLog.setUsername(JWTusername);
                balanceLog.setBalanceBefore(String.valueOf(Balance));
                balanceLog.setBalanceAfter(String.valueOf(after));
                balanceLog.setAmount("+"+reword.getItemNum());
                logService.newBalanceLog(balanceLog);
                continue;
            }
            //是否已拥有，拥有就更新，不拥有就插入
            String num=userInfoService.getItemNum(reword.getItemID()).getString("num");
            if(num==null){
                //LogUtil.showDebug("insert");
                activityDao.toWarehouse(JWTusername,reword);//插入语句

            }else{
                //LogUtil.showDebug("update");
                int before=Integer.valueOf(num);
                int after=before+Integer.valueOf( reword.getItemNum());
                userInfoService.setItemNum(reword.getItemID(),String.valueOf(after));
            }

            //日志
            itemLog.setDeltaQty("+"+reword.getItemNum());
            itemLog.setReason("活动领取");
            itemLog.setUsername(JWTusername);
            itemLog.setItemID(reword.getItemID());
            logService.newItemLog(itemLog);
        }

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

    public List<Participation> getParticipationByUsername(String username) throws IOException {
        List<Participation> participations=activityDao.getParticipationbyUsername(username);
        return participations;
    }
   // @CacheEvict
    public void updateUserProgress(Participation participation) throws IOException {
        activityDao.updateUserProgress(participation);
    }

    public Activity getSingleActivityByID(String ActivityID) throws IOException {
        Activity activity=null;
        activity=activityDao.getSingleActivityByID(ActivityID);
        return activity;
    }
}
