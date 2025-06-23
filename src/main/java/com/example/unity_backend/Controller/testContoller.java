package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class testContoller {
    private final testService testservice;

    @Autowired
    public testContoller(testService service){
        this.testservice=service;
    }

    @RequestMapping("/test")
    public JSONObject testPath() throws SQLException, ClassNotFoundException {
        return testservice.testPath();
    }
    @RequestMapping("/centos")
    public JSONObject testCentos(){
        JSONObject json=new JSONObject();
        json.put("code",000);
        return json;
    }
}
