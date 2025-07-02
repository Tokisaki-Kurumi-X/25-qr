package com.example.unity_backend.Dao.Activity;

import com.example.unity_backend.Entity.ActivityVO;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ActivityMapper {
    List<ActivityVO> getListandReward(String username);
    List<ActivityVO> updateActivity();
}
