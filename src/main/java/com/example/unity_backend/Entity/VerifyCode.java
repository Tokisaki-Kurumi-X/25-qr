package com.example.unity_backend.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class VerifyCode {
    private String MailAddress;
    private String Code;
    private Date ExpiresAt;
    private int MailStatus;
}
