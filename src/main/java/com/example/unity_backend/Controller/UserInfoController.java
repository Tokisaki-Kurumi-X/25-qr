package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Db.Db;
import com.example.unity_backend.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    private UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService1){
        this.userInfoService=userInfoService1;
    }

    @GetMapping("/balance")
    public JSONObject getBalance() throws IOException {
        return userInfoService.getUserBalance();
    }

    @GetMapping("/warehouse")
    public JSONObject getWarehouse() throws IOException {
        return userInfoService.getUserWarehouse();

    }

    @GetMapping("/store")
    public JSONObject getStore() throws IOException {
        return  userInfoService.getUserStore();

    }
}
