package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reset")
public class ResetPwController {
    @PutMapping("/mail/code")
    public JSONObject sendVerifyCode(String code){

        return null;
    }
}
