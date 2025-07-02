package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    private ActivityService activityService;
    @Autowired
    public ActivityController(ActivityService activityService1){
        this.activityService=activityService1;
    }

   @GetMapping("/list")
   public JSONObject getListandReward() throws IOException {
            return activityService.getRewardAndList();
    }

    @PostMapping("/new")
    public JSONObject participate(){

        return null;
    }

}
