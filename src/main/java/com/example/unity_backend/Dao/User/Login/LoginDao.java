package com.example.unity_backend.Dao.User.Login;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginDao {
    private Mybatis mybatis;
    private LoginMapper loginMapper;
    private User user;
    @Autowired
    public  LoginDao (Mybatis mybatis1) throws IOException {
        this.mybatis=mybatis1;
        this.mybatis.initMybatis();
        loginMapper= mybatis.getSqlSession().getMapper(LoginMapper.class);
    }

    public  User getUserByAccount(String account) throws IOException {
        user=null;
        //LogUtil.showDebug(account);
        user=loginMapper.getUserbyUsername(account);
        if(user==null){
            //LogUtil.showDebug("name not exist");
            user=loginMapper.getUserbyMailAddress(account);
        }
        //LogUtil.showDebug(user.toString());
        return user;
    }

    public User getPassword(String username){
        user=null;
        user=loginMapper.getPasswordbyUsername(username);
        return user;
    }


}
