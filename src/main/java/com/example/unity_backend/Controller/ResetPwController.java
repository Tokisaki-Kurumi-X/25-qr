package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Service.ResetPWService;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/reset")
public class ResetPwController {
    private ResetPWService resetPWService;

    @Autowired
    public ResetPwController(ResetPWService resetPWService1){
        this.resetPWService=resetPWService1;
    }

    @PutMapping("/mail/code")
    public JSONObject sendVerifyCode(@RequestParam("account") String account) throws IOException {
        //LogUtil.showDebug(account);
        return resetPWService.sendCode(account);
    }

    @GetMapping("/mail/verify")
    public JSONObject verifyCode(@RequestParam("code") String code,@RequestParam("account") String account) throws IOException {
        return  resetPWService.verifyCode(code,account);
    }
    @PutMapping("/password")
    public JSONObject resetPassword(@RequestBody JSONObject json) throws IOException {

        return resetPWService.resetPassword(json);
    }
}
