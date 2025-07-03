package com.example.unity_backend.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class GameRecord {
    private String UserName;
    private Date PlayTime;
    private float CostTime;
    private float Grade;
    private boolean isMaximumUpdate;
    private int RecordID;
    private int level;
}
