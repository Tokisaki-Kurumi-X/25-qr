package com.example.unity_backend.Dao.User.Register;

import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Entity.VerifyCode;

import java.util.Date;

public interface RegisterMapper {
    User getUserByUserName(String username);
    VerifyCode getUserByMailAddress(String mail);
    VerifyCode getVerifyCodebyMail(String mail);
    int upsertVerifyCode(String mail,String code,Date expire);
    int newUser(String name,String mail,String password);
    void newUserRole(String username,String roleid);
    void mailConfirm(String mail);
}
