package com.example.unity_backend.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class Activity {
    private String ActivityID;
    private String ActName;
    private Date StartAt;
    private Date EndAt;
    private String ActStatus;
    private String TotalProgress;
}
