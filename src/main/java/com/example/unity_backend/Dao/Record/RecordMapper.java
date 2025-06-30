package com.example.unity_backend.Dao.Record;

import com.example.unity_backend.Entity.GameRecord;

import java.util.Date;
import java.util.List;

public interface RecordMapper {
        List<GameRecord> getAllGameRecord(String username);
        int newGameRecord(GameRecord gameRecord);
        float getMaxGrade(String username);
        int setMaxRecordHistory(GameRecord gameRecord);
        List<GameRecord> getAllMaxUpdateGameRecord(String username);
}
