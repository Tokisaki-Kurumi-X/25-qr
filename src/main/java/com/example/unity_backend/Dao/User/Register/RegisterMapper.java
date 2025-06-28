package com.example.unity_backend.Dao.User.Register;

import com.example.unity_backend.Entity.User;

public interface RegisterMapper {
    User getUserByUserName(String username);
    User getUserByMailAddress(String mail);
}
