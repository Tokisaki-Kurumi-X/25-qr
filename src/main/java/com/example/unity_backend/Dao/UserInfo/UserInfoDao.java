package com.example.unity_backend.Dao.UserInfo;

import com.example.unity_backend.Dao.Mybatis.Mybatis;
import com.example.unity_backend.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    //放进redis,管理员新增商城物品或者购买了再改
    @Cacheable(value = "storeItem",key = "#username")
    public List<StoreVO> getStoreItem(String username) throws IOException {
        openDB();
        List<StoreVO> storeVO=userInfoMapper.getStoreItem(username);
        closeDB();
        return  storeVO;
    }

    public Item getPurchaseItem(String itemid) throws IOException {
        openDB();
        Item item=userInfoMapper.getPurchaseItem(itemid);
        closeDB();
        return item;
    }

    public UserWarehouse getAWarehouseItem(String username,String itemid) throws IOException {
        openDB();
        UserWarehouse userWarehouse=userInfoMapper.getAUserWarehouseItem(username,itemid);
        closeDB();
        return userWarehouse;
    }

    public void updateUserWarehouse(UserWarehouse userWarehouse) throws IOException {
        openDB();
        userInfoMapper.updateUserWarehouse(userWarehouse);
        closeDB();
    }

    @CacheEvict(value = "storeItem",key = "#userWarehouse.UserName")
    public void newUserWarehouse(UserWarehouse userWarehouse) throws IOException {
        openDB();
        userInfoMapper.newUserWarehouse(userWarehouse);
        closeDB();
    }

    public void UpdateUserBalance(String username,String balance) throws IOException {
        openDB();
        userInfoMapper.updateUserBalance(username,balance);
        closeDB();
    }

    @Cacheable(value = "userInfo",key = "#username")
    public String getNicknamebyUsername(String username) throws IOException {
        openDB();
        String name=userInfoMapper.getNicknamebyUsername(username);
        closeDB();
        return name;
    }

}
