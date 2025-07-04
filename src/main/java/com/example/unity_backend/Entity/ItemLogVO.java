package com.example.unity_backend.Entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ItemLogVO extends ItemLog implements Serializable {
    private static final long serialVersionUID = 6989511581662860272L;  // Match the serialVersionUID from the error message
    private String ItemName;

}
