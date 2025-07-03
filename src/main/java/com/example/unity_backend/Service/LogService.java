package com.example.unity_backend.Service;

import com.example.unity_backend.Dao.Log.LogDao;
import com.example.unity_backend.Entity.Item;
import com.example.unity_backend.Entity.ItemLog;
import com.example.unity_backend.Entity.LoginLog;
import com.example.unity_backend.Utils.IPUtils.IPUtil;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class LogService {
    @Autowired
    private LogDao logDao;
    @Autowired
    private HttpServletRequest httpServletRequest;


    public void newLoginlog(String username){
        LoginLog loginLog=new LoginLog();
        loginLog.setIpAddress(IPUtil.getIpAddress(httpServletRequest));
        loginLog.setUsername(username);
        loginLog.setOccurredAt((new Date(System.currentTimeMillis())));
        logDao.newLoginLog(loginLog);
    }
    //purchase
    //reward
    public void newItemLog(ItemLog itemLog){
        itemLog.setOccurredAt((new Date(System.currentTimeMillis())));
        logDao.newItemLog(itemLog);
    }

    public void newBalanceLog(){

    }
}
