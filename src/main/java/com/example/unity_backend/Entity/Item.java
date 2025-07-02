package com.example.unity_backend.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ItemID;
    private String ItemName;
    private String ItemPrice;
    private String CurrentDiscount;
    private String isDuplicate;
}
