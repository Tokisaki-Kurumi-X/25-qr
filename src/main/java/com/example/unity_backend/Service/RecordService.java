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
import java.io.IOException;


@Service
public class RecordService {
    private RecordDao recordDao;
    private JSONObject res;


    @Autowired
    public RecordService(RecordDao recordDao1,HttpServletRequest request1){
        this.recordDao=recordDao1;
        res=new JSONObject();
    }

    public JSONObject newRecord(GameRecord gameRecord) throws IOException {
        res.clear();
        //init
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();//Filter已做校验，不会空
        gameRecord.setUserName(username);

        //LogUtil.showDebug(gameRecord.toString());
        //new

        //LogUtil.showDebug(String.valueOf(gameRecord.getRecordID()));
        //update
        float maxHistory=recordDao.getMaxGradeRecord(gameRecord.getUserName());
       // LogUtil.showDebug("max is "+maxHistory);
        boolean shouldUpdate=false;
        if(gameRecord.getGrade()>maxHistory){
            shouldUpdate=true;
            res.put("isMax","true");
        }else {
            res.put("isMax","false");
        }
        recordDao.newGameRecord(gameRecord);
        if(shouldUpdate){
            recordDao.updateMaxRecord(gameRecord);
        }
        res.put("res_msg","success");
        return res;
    }
}
