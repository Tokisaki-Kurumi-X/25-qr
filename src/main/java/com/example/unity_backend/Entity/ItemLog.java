package com.example.unity_backend.Entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ItemLog  implements Serializable{
    private static final long serialVersionUID=1L;
    private String LogID;
    private String Username;
    private String ItemID;
    private String DeltaQty;
    private String Reason;
    private Date OccurredAt;
}
