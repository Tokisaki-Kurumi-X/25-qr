package com.example.unity_backend.Dao.Activity;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Dao.Record.RecordMapper;
import com.example.unity_backend.Entity.*;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ActivityDao {
    private Mybatis mybatis;
    private ActivityMapper activityMapper;
    private String module="activities:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;  // 注入 RedisTemplate

    @Autowired
    public ActivityDao(Mybatis mybatis1){
        this.mybatis=mybatis1;

    }

    private void openDB() throws IOException {
        this.mybatis.initMybatis();
        this.activityMapper=mybatis.getSqlSession().getMapper(ActivityMapper.class);
    }
    private void closeDB(){
        this.mybatis.closeSqlSession();
    }


    public List<ActivityVO> getListandReword(String username) throws IOException {
        String cacheKey = module+username;
        @SuppressWarnings("unchecked")
        List<ActivityVO> cached = (List<ActivityVO>) redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            LogUtil.showDebug("从缓存获取 allActivities");
            return cached;
        }

        openDB();
        List<ActivityVO> list=activityMapper.getListandReward(username);
        closeDB();
        // 设置过期时间为活动列表中的最早活动结束时间
        if (list != null && !list.isEmpty()) {
            long minEndAt = list.stream()
                    .mapToLong(activity -> activity.getEndAt().getTime())
                    .min()
                    .orElse(0L);  // 获取最早的结束时间
            long ttl = minEndAt - System.currentTimeMillis();  // 计算剩余时间
            // 手动写缓存
            redisTemplate.opsForValue().set(cacheKey, list, ttl, TimeUnit.MILLISECONDS);
            //手动删除缓存
            //redisTemplate.delete(cacheKey);
            LogUtil.showDebug("写入缓存 allActivities，TTL=" + ttl/1000 + "s");
        }
        return list;
    }


    public void participate(ParticipateDTO participateDTO) throws IOException {
        openDB();
        activityMapper.participate(participateDTO);
        String cacheKey = module+participateDTO.getUserName();
        redisTemplate.delete(cacheKey);
        closeDB();
    }

    @CacheEvict(value = "storeItem",key = "#participateDTO.UserName")
    public void getReward(ParticipateDTO participateDTO) throws IOException {
        openDB();
        activityMapper.getReward(participateDTO);
        //redis
        String cachekey=module+participateDTO.getUserName();
        //LogUtil.showDebug(cachekey);
        redisTemplate.delete(cachekey);
        closeDB();
    }

    public void updateUserProgress(Participation participation) throws IOException {
        openDB();
        String cachekey=module+participation.getUserName();
        //LogUtil.showDebug(cachekey);
        redisTemplate.delete(cachekey);
        LogUtil.showDebug(participation.toString());
        activityMapper.updateUserProgress(participation);
        closeDB();
    }

    public List<Reword> getRewardListByActID(String ActivityID) throws IOException {
        openDB();
        List<Reword> rewords=activityMapper.getRewordListByActID(ActivityID);
        closeDB();
        return rewords;
    }

    public void toWarehouse(String username,Reword reword) throws IOException {
        openDB();
        String cachekey=module+username;
        redisTemplate.delete(cachekey);
        //领取过了,是否已领取栏改变
        //LogUtil.showDebug(reword.getItemID());
        activityMapper.toWarehouse(username,reword.getItemID(),reword.getItemNum());
        closeDB();
    }

    public List<Participation> getParticipationbyUsername(String username) throws IOException {
        openDB();
        List<Participation> participation=activityMapper.getParticipationByUsername(username);
        closeDB();
        return participation;
    }

    public Activity getSingleActivityByID(String ActivityID) throws IOException {
        openDB();
        Activity activity=activityMapper.getSingleActivityByID(ActivityID);
        closeDB();
        return activity;
    }
}
