package com.example.unity_backend.Dao.Activity;

import com.example.unity_backend.Entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ActivityMapper {
    List<ActivityVO> getListandReward(String username);
    List<ActivityVO> updateActivity();
    void participate(ParticipateDTO participateDTO);
    void getReward(ParticipateDTO participateDTO);
    void updateUserProgress(Participation participation);
    List<Reword> getRewordListByActID(String ActivityID);
    void toWarehouse(String username,String ItemID,String Quantity);
    List<Participation> getParticipationByUsername(String username);
    Activity getSingleActivityByID(String ActivityID);
}
