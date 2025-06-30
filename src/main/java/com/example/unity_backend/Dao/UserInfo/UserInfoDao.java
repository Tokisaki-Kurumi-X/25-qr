package com.example.unity_backend.Dao.UserInfo;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Dao.Record.RecordMapper;
import com.example.unity_backend.Entity.ItemDTO;
import com.example.unity_backend.Entity.StoreVO;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Entity.WarehouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class UserInfoDao {
    private Mybatis mybatis;
    private UserInfoMapper userInfoMapper;

    @Autowired
    public UserInfoDao(Mybatis mybatis1){
        this.mybatis=mybatis1;
    }

    private void openDB() throws IOException {
        this.mybatis.initMybatis();
       this.userInfoMapper=mybatis.getSqlSession().getMapper(UserInfoMapper.class);
    }
    private void closeDB(){
        this.mybatis.closeSqlSession();
    }

    public User getUserBalance(String username) throws IOException {
        openDB();
        User user=userInfoMapper.getUserBalance(username);
        closeDB();
        return  user;
    }

    public WarehouseVO getUserWarehouse(String username) throws IOException {
        openDB();
        WarehouseVO warehouseVO=userInfoMapper.getUserWareHouse(username);
        closeDB();
        return warehouseVO;
    }

    //放进redis
    public List<StoreVO> getStoreItem(String username) throws IOException {
        openDB();
        List<StoreVO> storeVO=userInfoMapper.getStoreItem(username);
        closeDB();
        return  storeVO;
    }
}
