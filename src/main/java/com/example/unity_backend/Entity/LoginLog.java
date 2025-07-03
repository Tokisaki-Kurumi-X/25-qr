package com.example.unity_backend.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class LoginLog {
    private String LoginID;
    private String Username;
    private String ipAddress;
    private Date OccurredAt;
}
