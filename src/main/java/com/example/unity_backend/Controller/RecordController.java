package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Entity.GameRecord;
import com.example.unity_backend.Service.RecordService;
import com.example.unity_backend.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gamerecord")
public class RecordController {
    private RecordService recordService;
    @Autowired
    public RecordController(RecordService recordService1){
        this.recordService=recordService1;
    }
    @PostMapping("/new")
    public JSONObject newGameRecord(@RequestBody GameRecord gameRecord){
        recordService.newRecord(gameRecord);
        return  null;
    }
}
