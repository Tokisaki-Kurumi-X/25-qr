package com.example.unity_backend.Entity;

import lombok.Data;

@Data
public class Item {
    private String ItemID;
    private String ItemName;
    private String ItemPrice;
    private String CurrentDiscount;
    private String isDuplicate;
}
