package com.example.unity_backend.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class GameRecord {
    private String Username;
    private Date PlayTime;
    private String PlaySong;
    private float Grade;
    private boolean isMaximumUpdate;
    private int RecordID;
}
