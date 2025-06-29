package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Record.RecordDao;
import com.example.unity_backend.Entity.GameRecord;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class RecordService {
    private RecordDao recordDao;
    private JSONObject res;


    @Autowired
    public RecordService(RecordDao recordDao1,HttpServletRequest request1){
        this.recordDao=recordDao1;
        res=new JSONObject();
    }

    public JSONObject newRecord(GameRecord gameRecord){
        res.clear();
        //init
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();//Filter已做校验，不会空
        gameRecord.setUsername(username);
        //LogUtil.showDebug(gameRecord.toString());
        //new

        //update

        return res;
    }
}
