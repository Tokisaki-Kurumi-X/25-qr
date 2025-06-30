package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.UserInfo.UserInfoDao;
import com.example.unity_backend.Entity.ItemDTO;
import com.example.unity_backend.Entity.StoreVO;
import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Entity.WarehouseVO;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserInfoService {
    private UserInfoDao userInfoDao;
    private JSONObject res;
    private Authentication authentication;
    private String JWTusername;

    @Autowired
    public  UserInfoService(UserInfoDao userInfoDao1){
        this.userInfoDao=userInfoDao1;
        res=new JSONObject();
    }
    private void getJWTUsername(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        this.JWTusername=authentication.getName();
    }
    public JSONObject getUserBalance() throws IOException {
        res.clear();
        getJWTUsername();
        //LogUtil.showDebug(JWTusername);
        User user=userInfoDao.getUserBalance(JWTusername);
        res.put("balance",user.getBalance());
        return  res;
    }

    public JSONObject getUserWarehouse() throws IOException {
        res.clear();
        getJWTUsername();
        WarehouseVO warehouseVO=userInfoDao.getUserWarehouse(JWTusername);
//        for (WarehouseVO vo : warehouseVO) {
//            LogUtil.showDebug(vo.toString());
//        }
        res.put("aaData",warehouseVO);
        return res;
    }

    public JSONObject getUserStore() throws IOException {
        res.clear();
        getJWTUsername();
        List<StoreVO> storeVOS=userInfoDao.getStoreItem(JWTusername);
        LogUtil.showDebug(storeVOS.toString());
        res.put("aaData",storeVOS);
        return res;
    }
}
