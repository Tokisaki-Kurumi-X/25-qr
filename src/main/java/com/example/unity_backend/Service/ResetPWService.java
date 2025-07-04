package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.ResetPW.ResetPWDao;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Entity.VerifyCode;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ResetPWService {
    private JSONObject res;
    private ResetPWDao resetPWDao;
    private RegisterService registerService;
    private Authentication authentication;
    private String JWTusername;
    private LoginService loginService;
    private void getJWTUsername(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        this.JWTusername=authentication.getName();
    }
    @Autowired
    public ResetPWService(RegisterService registerService1,LoginService loginService1,ResetPWDao resetPWDao1){
        this.res=new JSONObject();
        this.registerService=registerService1;
        this.loginService=loginService1;
        this.resetPWDao=resetPWDao1;
    }

    public JSONObject sendCode(String account) throws IOException {
        LogUtil.showDebug(account);
        res.clear();
        //验证是否存在（供首页重置时使用）
        JSONObject user=loginService.VerifyAccount(account);
        if(user.getString("isExist").equals("false")){
            res.put("res_msg","invalid account");
            res.put("status","failed");
            return res;
        }
        //此时存在该账户
        //需要获取账户邮箱
        User user1=loginService.getUserByAccount(account);
        VerifyCode verifyCode=new VerifyCode();
        verifyCode.setMailAddress(user1.getMailAddress());
        registerService.sendCode(verifyCode);
        res.put("status","success");
        return res;
    }

    public JSONObject verifyCode(String code,String account) throws IOException {
        res.clear();
        User user1=loginService.getUserByAccount(account);
        VerifyCode verifyCode=new VerifyCode();
        verifyCode.setMailAddress(user1.getMailAddress());
        verifyCode.setCode(code);
        return registerService.verifyMailAddressbyCode(verifyCode);
    }

    public JSONObject resetPassword(JSONObject json) throws IOException {
        res.clear();
        User user1=loginService.getUserByAccount(json.getString("account"));
        String username=user1.getUsername();
        String password=json.getString("password");
        resetPWDao.resetPassword(username,password);
        res.put("status","success");
        return res;
    }
}
