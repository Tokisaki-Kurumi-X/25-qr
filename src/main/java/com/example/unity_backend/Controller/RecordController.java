package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Entity.GameRecord;
import com.example.unity_backend.Service.RecordService;
import com.example.unity_backend.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/gamerecord")
public class RecordController {
    private RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService1) {
        this.recordService = recordService1;
    }

    @PostMapping("/new")
    public JSONObject newGameRecord(@RequestBody GameRecord gameRecord) throws IOException {
        return recordService.newRecord(gameRecord);
    }

    @GetMapping("/query")
    public JSONObject getGameRecords(@RequestParam("type") String type,@RequestParam("level") String level) throws IOException {
        if (type.equals("normal")) {//get all
            return recordService.getAllRecords(level);
        } else if (type.equals("max")) {//max all
            return recordService.getMaxRecords(level);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("res_msg", "param is not valid");
        return jsonObject;
    }

    @GetMapping("/maxGrade")
    public JSONObject getMaxGrade() throws IOException {

        return recordService.getMaxGrade();
    }
}
