package com.example.unity_backend.Dao.Log;

import com.example.unity_backend.Entity.BalanceLog;
import com.example.unity_backend.Entity.ItemLog;
import com.example.unity_backend.Entity.LoginLog;

public interface LogMapper {
    void newLoginLog(LoginLog loginLog);
    void newItemLog(ItemLog itemLog);
    void newBalanceLog(BalanceLog balanceLog);
}
