package com.example.unity_backend.Dao.Log;

import com.example.unity_backend.Entity.BalanceLog;
import com.example.unity_backend.Entity.ItemLog;
import com.example.unity_backend.Entity.ItemLogVO;
import com.example.unity_backend.Entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
public interface LogMapper {
    void newLoginLog(LoginLog loginLog);
    void newItemLog(ItemLog itemLog);
    void newBalanceLog(BalanceLog balanceLog);
    List<BalanceLog> getBalanceLog(String username);
    List<ItemLogVO> getItemLog(String username);
    List<LoginLog> getLoginLog(String username);
}
