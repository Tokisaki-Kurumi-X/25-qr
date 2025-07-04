package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;
    @GetMapping("/login")
    public JSONObject getLoginLog() throws IOException {
        return  logService.getLoginLog();
    }
    @GetMapping("/balance")
    public JSONObject getBalanceLog() throws IOException {
        return logService.getBalanceLog();
    }
    @GetMapping("/item")
    public JSONObject getItemLog() throws IOException {
        return logService.getItemLog();
    }
}
