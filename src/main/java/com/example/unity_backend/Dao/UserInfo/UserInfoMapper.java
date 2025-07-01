package com.example.unity_backend.Dao.UserInfo;

import com.example.unity_backend.Entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserInfoMapper {
    User getUserBalance(String username);
    WarehouseVO getUserWareHouse(String username);
    List<StoreVO> getStoreItem(String username);
    Item getPurchaseItem(String itemid);
    UserWarehouse getAUserWarehouseItem(String username,String itemid);
    void updateUserWarehouse(UserWarehouse userWarehouse);
    void newUserWarehouse(UserWarehouse userWarehouse);
    void updateUserBalance(String username,String balance);
}
