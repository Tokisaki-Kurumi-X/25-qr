package com.example.unity_backend.Entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionId=1L;
    private String Username;
    private String Password;
    private String MailAddress;
    private String NickName;
    private double Balance;
    private String UserStatus;
    private Date changeNickCoolTime;
}
