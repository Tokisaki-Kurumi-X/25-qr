package com.example.unity_backend.Dao.User.Login;

import com.example.unity_backend.Entity.User;

public interface LoginMapper {
    User  getUserbyUsername(String acccount);
    User  getUserbyMailAddress(String account);
    User  getPasswordbyUsername(String username);
}
