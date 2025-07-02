package com.example.unity_backend.Dao.Activity;

import com.example.unity_backend.Entity.ActivityVO;
import com.example.unity_backend.Entity.ParticipateDTO;
import com.example.unity_backend.Entity.Participation;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ActivityMapper {
    List<ActivityVO> getListandReward(String username);
    List<ActivityVO> updateActivity();
    void participate(ParticipateDTO participateDTO);
    void getReward(ParticipateDTO participateDTO);
    void updateUserProgress(Participation participation);
}
