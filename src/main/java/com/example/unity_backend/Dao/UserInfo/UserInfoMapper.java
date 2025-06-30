package com.example.unity_backend.Dao.UserInfo;

import com.example.unity_backend.Entity.ItemDTO;
import com.example.unity_backend.Entity.StoreVO;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Entity.WarehouseVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserInfoMapper {
    User getUserBalance(String username);
    WarehouseVO getUserWareHouse(String username);
    List<StoreVO> getStoreItem(String username);
}
