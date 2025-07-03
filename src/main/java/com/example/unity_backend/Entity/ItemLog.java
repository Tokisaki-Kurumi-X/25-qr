package com.example.unity_backend.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class ItemLog {
    private String LogID;
    private String Username;
    private String ItemID;
    private String DeltaQty;
    private String Reason;
    private Date OccurredAt;
}
