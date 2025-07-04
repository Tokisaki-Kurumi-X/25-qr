package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Log.LogDao;
import com.example.unity_backend.Entity.*;
import com.example.unity_backend.Utils.IPUtils.IPUtil;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class LogService {
    private LogDao logDao;
    @Autowired
    private HttpServletRequest httpServletRequest;
    private Authentication authentication;
    private String JWTusername;
    private JSONObject res;
    @Autowired
    public LogService(LogDao logDao1){
        this.res=new JSONObject();
        this.logDao=logDao1;
    }

    private void getJWTUsername(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        this.JWTusername=authentication.getName();

    }

    //login 1
    public void newLoginlog(String username) throws IOException {
        LoginLog loginLog=new LoginLog();
        loginLog.setIpAddress(IPUtil.getIpAddress(httpServletRequest));
        loginLog.setUsername(username);
        loginLog.setOccurredAt((new Date(System.currentTimeMillis())));
        LogUtil.showDebug(loginLog.toString());
        logDao.newLoginLog(loginLog);
    }
    //purchase 1
    //reward 1
    //use 1
    public void newItemLog(ItemLog itemLog) throws IOException {
        itemLog.setOccurredAt((new Date(System.currentTimeMillis())));
        logDao.newItemLog(itemLog);
    }

    //use(Store) 1
    //reward 1
    public void newBalanceLog(BalanceLog balanceLog) throws IOException{
        balanceLog.setOccurredAt((new Date(System.currentTimeMillis())));
        logDao.newBalanceLog(balanceLog);
    }

    public JSONObject getBalanceLog() throws IOException {
        res.clear();
        getJWTUsername();
        //LogUtil.showDebug(JWTusername);
        List<BalanceLog> balanceLogs=logDao.getBalanceLog(JWTusername);
        res.put("logType","BalanceLog");
        res.put("aaDaa",balanceLogs);
        return res;
    }

    public JSONObject getItemLog() throws IOException {
        res.clear();
        getJWTUsername();
        List<ItemLogVO> itemLogs=logDao.getItemLog(JWTusername);
        res.put("logType","ItemLog");
        res.put("aaDaa",itemLogs);
        return res;
    }

    public JSONObject getLoginLog() throws IOException {
        res.clear();
        getJWTUsername();
        List<LoginLog> loginLogs=logDao.getLoginLog(JWTusername);
        res.put("logType","LoginLog");
        res.put("aaDaa",loginLogs);
        return res;
    }
}
