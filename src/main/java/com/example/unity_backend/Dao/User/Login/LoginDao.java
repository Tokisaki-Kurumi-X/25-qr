package com.example.unity_backend.Dao.User.Login;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Dao.User.Register.RegisterMapper;
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

    }


    private void openDB() throws IOException {
        this.mybatis.initMybatis();
        this.loginMapper=mybatis.getSqlSession().getMapper(LoginMapper.class);
    }
    private void closeDB(){
        this.mybatis.closeSqlSession();
    }

    public  User getUserByAccount(String account) throws IOException {
        openDB();
        user=null;
        //LogUtil.showDebug(account);
        user=loginMapper.getUserbyUsername(account);
        if(user==null){
            //LogUtil.showDebug("name not exist");
            user=loginMapper.getUserbyMailAddress(account);
        }
        //LogUtil.showDebug(user.toString());
        closeDB();
        return user;
    }

    public User getPassword(String username) throws IOException {
        openDB();
        user=null;
        user=loginMapper.getPasswordbyUsername(username);
        closeDB();
        return user;
    }


}
