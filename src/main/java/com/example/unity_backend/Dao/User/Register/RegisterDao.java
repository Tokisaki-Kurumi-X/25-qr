package com.example.unity_backend.Dao.User.Register;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Entity.VerifyCode;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RegisterDao {
    private Mybatis mybatis;
    private RegisterMapper registerMapper;
    private User user;
    @Autowired
    public RegisterDao(Mybatis mybatis1) throws IOException {
        this.mybatis=mybatis1;


    }

    private void openDB() throws IOException {
        this.mybatis.initMybatis();
        this.registerMapper=mybatis.getSqlSession().getMapper(RegisterMapper.class);
    }
    private void closeDB(){
        this.mybatis.closeSqlSession();
    }
    public User verifyUsername(String username) throws IOException {
        openDB();
        user=null;
        user=registerMapper.getUserByUserName(username);
        closeDB();
        return user;
    }

    public VerifyCode verifyMailAddress(String mail) throws IOException {
        openDB();
        user=null;
        VerifyCode a=registerMapper.getUserByMailAddress(mail);//mail全局唯一，由DDL决定的

        closeDB();
        return a;
    }
    public User getUserbyUsername(String username) throws IOException {
        openDB();
        user=null;
        user=registerMapper.getUserByUserName(username);
        closeDB();
        return user;
    }
    public void upsertVerifyCode(String mail,String code) throws IOException {
        openDB();
        //LogUtil.showDebug("MailAddress to be updated: " + mail);
        //设置code的有效期：5min
        int TOKEN_EXPIRE = 5*60*1000;
        Date exp = new Date(System.currentTimeMillis() + TOKEN_EXPIRE);
        int updatedRows = registerMapper.upsertVerifyCode(mail, code, exp);
        closeDB();
        //this.mybatis.closeSqlSession();
    }

    public VerifyCode getVerifyCodebyMail(String mail) throws IOException {
        openDB();
        VerifyCode a=registerMapper.getVerifyCodebyMail(mail);
        closeDB();
        return a;
    }

    public void newUser(User user) throws IOException {
        openDB();
        registerMapper.newUser(user.getUsername(),user.getMailAddress(),user.getPassword());
        closeDB();
    }

    public void newUserRole(User user) throws IOException {
        openDB();
        registerMapper.newUserRole(user.getUsername(),"1");
        closeDB();
    }

    public void mailConfirm(User user) throws IOException {
        openDB();
        registerMapper.mailConfirm(user.getMailAddress());
        closeDB();
    }

    @CacheEvict(value = "userInfo",key="#user.Username")
    public void setNickname(User user) throws IOException {
        openDB();
        LogUtil.showDebug(user.toString());
        registerMapper.setNickname(user);
        closeDB();
    }
}
