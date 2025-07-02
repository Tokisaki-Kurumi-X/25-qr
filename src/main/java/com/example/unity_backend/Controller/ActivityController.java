package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Entity.Activity;
import com.example.unity_backend.Entity.ActivityVO;
import com.example.unity_backend.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public JSONObject participate(@RequestBody Activity activity) throws IOException {
        return activityService.Participate(activity);
    }

    @PutMapping("/reward")
    public JSONObject getReward(@RequestBody Activity activity) throws IOException {
        return activityService.getReward(activity);
    }

    @PutMapping("/progress")
    public JSONObject updateProgress(@RequestBody ActivityVO activityVO) throws IOException {
        return activityService.updateProgress(activityVO);
    }
}
