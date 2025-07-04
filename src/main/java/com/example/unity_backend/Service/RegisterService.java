package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.User.Login.LoginDao;
import com.example.unity_backend.Dao.User.Register.RegisterDao;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Entity.VerifyCode;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import com.example.unity_backend.Utils.MailUtils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Service
public class RegisterService {
    private MailUtil mailUtil;
    private RegisterDao registerDao;
    private JSONObject res;
    private Authentication authentication;
    private String JWTusername;

    private void getJWTUsername(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        this.JWTusername=authentication.getName();
    }
    @Autowired
    public  RegisterService(RegisterDao registerDao1,MailUtil mailUtil){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        this.registerDao=registerDao1;
        this.res=new JSONObject();
        this.mailUtil=mailUtil;
    }


    public JSONObject verifyUsername(User user1) throws IOException {
        res.clear();
        User user=registerDao.verifyUsername(user1.getUsername());
        //LogUtil.showDebug(user.toString());
        if(user==null){
            res.put("isExist","false");
        }else {
            res.put("isExist","true");
        }
        return  res;
    }

    public JSONObject verifyMailAddress(String mail) throws IOException {
        res.clear();
        VerifyCode verifyCode=registerDao.verifyMailAddress(mail);
        if(verifyCode==null){
            res.put("isExist","false");
        }else {
           // LogUtil.showDebug(verifyCode.toString());
            if(verifyCode.getMailStatus()!=3){
                res.put("isExist","false");
            }else {
                res.put("isExist","true");
            }
        }
        return res;
    }


    public JSONObject registerNewUser(User user) throws IOException {
        res.clear();
        //mailUtil.sendTextMailMessage("2259532295@qq.com","test","codeVerify");
        LogUtil.showDebug(user.toString());
        //user
        registerDao.newUser(user);
        //role
        registerDao.newUserRole(user);
        //mail
        registerDao.mailConfirm(user);
        res.put("status","success");
        return res;
    }

    public JSONObject sendCode(VerifyCode verifyCode) throws IOException {
        res.clear();
        //LogUtil.showDebug(json.toString());
        String code=String.valueOf((new Random()).nextInt(9000)+1000);
        JSONObject send_result=mailUtil.sendTextMailMessage(verifyCode.getMailAddress(),"注册验证码",code);
        if(send_result.getString("send_status").equals("success")){
            res.put("result_msg","发送成功");
            res.put("status","success");
        }else {
            res.put("result_msg","发送失败");
            res.put("err_msg",send_result.getString("err"));
        }

        //DB
        registerDao.upsertVerifyCode(verifyCode.getMailAddress(),code);
        return res;
    }

    public JSONObject verifyMailAddressbyCode(VerifyCode verifyCode) throws IOException {
        res.clear();
        VerifyCode resCode=registerDao.getVerifyCodebyMail(verifyCode.getMailAddress());
        LogUtil.showDebug("rescode: "+resCode.toString());
        LogUtil.showDebug("current: "+verifyCode);
        //1.判断是否过期
        Date now= new Date(System.currentTimeMillis());
        LogUtil.showDebug("now: "+now);
        if(now.after(resCode.getExpiresAt())){
            res.put("isMatch","false");
            res.put("status", "expired");
            res.put("message", "验证码已过期，请重新获取。");
            return res;
        }
        //2.判断是否对应
        if (!verifyCode.getCode().equals(resCode.getCode())) {
            // 前端传来的 code 和数据库不一致
            res.put("isMatch","false");
            res.put("status", "invalid");
            res.put("message", "验证码错误，请检查后重试。");
            return res;
        }
        res.put("isMatch","true");
        return res;
    }

    public JSONObject setNickname(User user) throws IOException {
        res.clear();
        //verify
        //LogUtil.showDebug(user.toString());
        getJWTUsername();

        user.setUsername(JWTusername);
        Date expire=getCooltime(user);
        if(expire==null){
            LogUtil.showDebug("null date");
        }
        //LogUtil.showDebug(expire.toString());
        if(expire.after(new Date(System.currentTimeMillis()))){
            res.put("status","failed");
            res.put("res_msg","Still in cooling-off");
            return res;
        }
        //set
        user.setChangeNickCoolTime(new Date(System.currentTimeMillis()+1000*60*60*24));
        registerDao.setNickname(user);
        res.put("status","success");
        return res;
    }

    private Date getCooltime(User user) throws IOException {
        User user1=registerDao.getUserbyUsername(user.getUsername());
        LogUtil.showDebug(user1.toString());
        return user1.getChangeNickCoolTime();
    }
}
