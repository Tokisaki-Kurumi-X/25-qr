package com.example.unity_backend.Entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BalanceLog implements Serializable {
    private static final long serialVersionUID=1L;
    private String LogID;
    private String Username;
    private String ChangeType;
    private String Amount;
    private String BalanceBefore;
    private String BalanceAfter;
    private Date OccurredAt;
}
