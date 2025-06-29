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
        //LogUtil.showDebug(request.getHeader("Authorization").toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=null;
        if (authentication != null) {
            username = authentication.getName();
        }
        LogUtil.showDebug(username);
        return res;
    }
}
