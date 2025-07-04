package com.example.unity_backend.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.UserInfo.UserInfoDao;

import com.example.unity_backend.Entity.*;
import com.example.unity_backend.Utils.IPUtils.IPUtil;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class UserInfoService {
    private UserInfoDao userInfoDao;
    private JSONObject res;
    private Authentication authentication;
    private String JWTusername;
    @Autowired
    private  LogService logService;
    @Autowired
    private HttpServletRequest httpServletRequest;
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
    //redis store
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
    //写日志
    public JSONObject purchaseItem(StoreItemVO storeItemVO) throws IOException {
        res.clear();
        getJWTUsername();
        double price=getPrice(storeItemVO);
        //判断余额
        JSONObject balance=getUserBalance();
        Double beforeBalance=Double.valueOf(balance.getString("balance"));
        res.remove("balance");
        if(beforeBalance<price){
            res.put("status","failed");
            res.put("res_msg","balance invalid");
            return  res;
        }
        //加入仓库

        UserWarehouse userWarehouse=userInfoDao.getAWarehouseItem(JWTusername,storeItemVO.getItemID());
        //LogUtil.showDebug(userWarehouse.toString());
        if(userWarehouse!=null){//update quantity
            int currentQuantity=Integer.valueOf(userWarehouse.getQuantity())+Integer.valueOf(storeItemVO.getQuantity());
            userWarehouse.setQuantity(String.valueOf(currentQuantity));

            userInfoDao.updateUserWarehouse(userWarehouse);
        }else {//insert
            LogUtil.showDebug("insert");
            userWarehouse=new UserWarehouse();
            userWarehouse.setQuantity(storeItemVO.getQuantity());
            userWarehouse.setItemID(storeItemVO.getItemID());
            userWarehouse.setUserName(JWTusername);
            userInfoDao.newUserWarehouse(userWarehouse);
        }
        //扣减余额
        Double afterBalance=beforeBalance-price;
        userInfoDao.UpdateUserBalance(JWTusername,String.valueOf(afterBalance));
        //日志：余额+物品
        ItemLog itemLog=new ItemLog();
        itemLog.setItemID(storeItemVO.getItemID());
        itemLog.setUsername(JWTusername);
        itemLog.setDeltaQty("+"+storeItemVO.getQuantity());
        itemLog.setReason("商城购买");
        logService.newItemLog(itemLog);
        BalanceLog balanceLog=new BalanceLog();
        balanceLog.setAmount(String.valueOf(-price));
        balanceLog.setBalanceBefore(String.valueOf(beforeBalance));
        balanceLog.setBalanceAfter(String.valueOf(afterBalance));
        balanceLog.setUsername(JWTusername);
        balanceLog.setChangeType("商城消费");
        logService.newBalanceLog(balanceLog);

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

    public JSONObject getNicknameByUsername() throws IOException {
        res.clear();
        getJWTUsername();
        String nickname=userInfoDao.getNicknamebyUsername(JWTusername);
        //LogUtil.showDebug(IPUtil.getIpAddress(httpServletRequest));
        res.put("nickname",nickname);
        return res;
    }

    public JSONObject getItemNum(String itemID) throws IOException {
        res.clear();
        getJWTUsername();
        Integer num=0;
        num=userInfoDao.getItemNum(JWTusername,itemID);
        //LogUtil.showDebug("**********"+String.valueOf(userInfoDao.getItemNum(JWTusername,itemID)));
        res.put("num",num);
        return res;
    }

    public JSONObject setItemNum(String itemID,String itemnum) throws IOException {
        res.clear();
        getJWTUsername();
        userInfoDao.setItemNum(JWTusername,itemID,itemnum);
        //使用了道具，写日志
        ItemLog itemLog=new ItemLog();
        itemLog.setReason("关卡使用");
        itemLog.setDeltaQty("-"+"1");
        itemLog.setUsername(JWTusername);
        itemLog.setItemID(itemID);
        logService.newItemLog(itemLog);
        res.put("status","success");
        return res;
    }

    public void updateUserBalance(String username,String Balance) throws IOException {
        userInfoDao.UpdateUserBalance(username,Balance);
    }
}
