package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.User.Login.LoginDao;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Utils.JWTUtils.JWTUtil;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginService {
    private LoginDao loginDao;
    private JSONObject res;
    private JWTUtil jwtUtil;
    @Autowired
    public LoginService(LoginDao loginDao1,JWTUtil jwtUtil1){
        this.loginDao=loginDao1;
        this.res=new JSONObject();
        this.jwtUtil=jwtUtil1;
    }


    public JSONObject VerifyAccount(String account) throws IOException {
        res.clear();
        //LogUtil.showDebug(account);
        User user=loginDao.getUserByAccount(account);
        if(user!=null){
            res.put("isExist","true");
        }else {
            res.put("isExist","false");
        }
        return res;
    }

    public JSONObject VerifyPassword(JSONObject json) throws Exception {
        res.clear();
        User user=loginDao.getUserByAccount(json.getString("account"));
        //根据流程，此处user一定存在
        //LogUtil.showDebug(user.toString());
        user=loginDao.getPassword(user.getUsername());
        //LogUtil.showDebug(user.toString());
        if(user.getPassword().equals(json.getString("password"))){
            res.put("Match","true");
            //JWT
            res.put("JWT",jwtUtil.generateToken(jwtUtil.generateJson(user.getUsername(),"Unity")));
        }else {
            res.put("Match","false");
        }
        return res;
    }
}
