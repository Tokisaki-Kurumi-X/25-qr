package com.example.unity_backend.Entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Reword extends Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ItemNum;
}
