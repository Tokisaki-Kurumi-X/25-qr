package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Service.LoginService;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/login")
public class LoginController {
    private LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService1){
        this.loginService=loginService1;
    }

    @GetMapping("/verify")
    public JSONObject VerifyAccount(@RequestParam("account") String account) throws IOException {
        return loginService.VerifyAccount(account);
    }

    @PostMapping("/verify/password")
    public JSONObject VerifyPassword(@RequestBody JSONObject user) throws Exception {
        //前端存储邮箱，需要前端保持正确性
        //LogUtil.showDebug(user.toString());
        return  loginService.VerifyPassword(user);
    }

    @PostMapping("/reset")
    public JSONObject resetPassword(@RequestBody User user){
        return  null;
    }
}
