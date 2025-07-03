package com.example.unity_backend.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class BalanceLog {
    private String LogID;
    private String Username;
    private String ChangeType;
    private String Amount;
    private String BalanceBefore;
    private String BalanceAfter;
    private Date OccurredAt;
}
