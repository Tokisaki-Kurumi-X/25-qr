package com.example.unity_backend.Dao.User.Register;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RegisterDao {
    private Mybatis mybatis;
    private RegisterMapper registerMapper;
    private User user;
    @Autowired
    public RegisterDao(Mybatis mybatis1) throws IOException {
        this.mybatis=mybatis1;
        this.mybatis.initMybatis();
        this.registerMapper=mybatis.getSqlSession().getMapper(RegisterMapper.class);
    }

    public User verifyUsername(String username){
        user=null;
        user=registerMapper.getUserByUserName(username);
        return user;
    }

    public User verifyMailAddress(String mail) {
        user=null;
        user=registerMapper.getUserByMailAddress(mail);//mail全局唯一，由DDL决定的
        return user;
    }
}
