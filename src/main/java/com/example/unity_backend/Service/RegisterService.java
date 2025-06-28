package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.User.Login.LoginDao;
import com.example.unity_backend.Dao.User.Register.RegisterDao;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import com.example.unity_backend.Utils.MailUtils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private MailUtil mailUtil;
    private RegisterDao registerDao;
    private JSONObject res;

    @Autowired
    public  RegisterService(RegisterDao registerDao1,MailUtil mailUtil){
        this.registerDao=registerDao1;
        this.res=new JSONObject();
        this.mailUtil=mailUtil;
    }


    public JSONObject verifyUsername(String username) {
        res.clear();
        User user=registerDao.verifyUsername(username);
        //LogUtil.showDebug(user.toString());
        if(user==null){
            res.put("isExist","false");
        }else {
            res.put("isExist","true");
        }
        return  res;
    }

    public JSONObject verifyMailAddress(String mail) {
        res.clear();
        User user=registerDao.verifyMailAddress(mail);
        if(user==null){
            res.put("isExist","false");
        }else {
            res.put("isExist","true");
        }
        return res;
    }

    public JSONObject registerNewUser(User user) {
        res.clear();
        mailUtil.sendTextMailMessage("2259532295@qq.com","test","codeVerify");
        return res;
    }
}
