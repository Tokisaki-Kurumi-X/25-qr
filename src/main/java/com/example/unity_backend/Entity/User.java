package com.example.unity_backend.Entity;

import lombok.Data;

@Data
public class User {
    private String Username;
    private String Password;
    private String MailAddress;
    private String NickName;
    private double Balance;
    private String UserStatus;
}
