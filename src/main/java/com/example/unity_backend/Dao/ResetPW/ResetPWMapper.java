package com.example.unity_backend.Dao.ResetPW;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
public interface ResetPWMapper {
    void resetPassword(String username,String password);
}
