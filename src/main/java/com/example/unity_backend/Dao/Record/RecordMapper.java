package com.example.unity_backend.Dao.Record;

import com.example.unity_backend.Entity.GameRecord;

import java.util.Date;
import java.util.List;

public interface RecordMapper {
        List<GameRecord> getAllGameRecord(String username);
        int newGameRecord(String username, String playsong, Date playtime,float grade);
        GameRecord getMaxGrade(String username);
        int setMaxRecordHistory(String recordid);
        List<GameRecord> getAllMaxUpdateGameRecord(String username);
}
