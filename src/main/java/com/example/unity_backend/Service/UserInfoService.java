package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.UserInfo.UserInfoDao;

import com.example.unity_backend.Entity.*;
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
        //LogUtil.showDebug(storeVOS.toString());
        res.put("aaData",storeVOS);
        return res;
    }

    public JSONObject purchaseItem(StoreItemVO storeItemVO) throws IOException {
        res.clear();
        getJWTUsername();
        double price=getPrice(storeItemVO);
       // LogUtil.showDebug("price "+String.valueOf(price));
        JSONObject balance=getUserBalance();
       // LogUtil.showDebug(balance.toString());
        Double Balance=Double.valueOf(balance.getString("balance"));
        res.remove("balance");
        if(Balance<price){
            res.put("status","failed");
            res.put("res_msg","balance invalid");
            return  res;
        }
        //加入仓库

        UserWarehouse userWarehouse=userInfoDao.getAWarehouseItem(JWTusername,storeItemVO.getItemID());

        if(userWarehouse!=null){//update quantity
            int currentQuantity=Integer.valueOf(userWarehouse.getQuantity())+Integer.valueOf(storeItemVO.getQuantity());
            userWarehouse.setQuantity(String.valueOf(currentQuantity));
            userInfoDao.updateUserWarehouse(userWarehouse);
        }else {//insert
            userWarehouse=new UserWarehouse();
            userWarehouse.setQuantity(storeItemVO.getQuantity());
            userWarehouse.setItemID(storeItemVO.getItemID());
            userWarehouse.setUserName(JWTusername);
            userInfoDao.newUserWarehouse(userWarehouse);
        }
        //扣减余额
        Balance=Balance-price;
        userInfoDao.UpdateUserBalance(JWTusername,String.valueOf(Balance));

        res.put("status","success");
        return res;
    }

    private double getPrice(StoreItemVO storeItemVO) throws IOException {
        Double price=0.0;
        Item res=userInfoDao.getPurchaseItem(storeItemVO.getItemID());
        //LogUtil.showDebug(res.toString());
        price=Double.valueOf(res.getItemPrice())*Integer.valueOf(storeItemVO.getQuantity());
        price*=(1-Double.valueOf(res.getCurrentDiscount()));
        return price;
    }

    public JSONObject getNicknameByUsername(String username) throws IOException {
        res.clear();
        String nickname=userInfoDao.getNicknamebyUsername(username);
        res.put("nickname",nickname);
        return res;
    }
}
