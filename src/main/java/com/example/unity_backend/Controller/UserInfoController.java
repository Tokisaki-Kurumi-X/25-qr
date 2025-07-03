package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.Db.Db;
import com.example.unity_backend.Entity.StoreItemVO;
import com.example.unity_backend.Service.UserInfoService;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    private UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService1){
        this.userInfoService=userInfoService1;
    }

    @GetMapping("/balance")
    public JSONObject getBalance() throws IOException {
        return userInfoService.getUserBalance();
    }

    @GetMapping("/warehouse")
    public JSONObject getWarehouse() throws IOException {
        return userInfoService.getUserWarehouse();

    }

    @GetMapping("/store")
    public JSONObject getStore() throws IOException {
        return  userInfoService.getUserStore();
    }

    @PostMapping("/purchase")
    public JSONObject purchaseItem(@RequestBody StoreItemVO storeItemVO) throws IOException {
        return userInfoService.purchaseItem(storeItemVO);
    }

    @GetMapping("/nickname")
    public JSONObject getNicknameByUsername(@RequestParam String username) throws IOException {
        //LogUtil.showDebug(username);
        return userInfoService.getNicknameByUsername(username);
    }

    @GetMapping("/getnum")
    public JSONObject getItemNUm(@RequestParam("itemid") String itemid) throws IOException {
        return userInfoService.getItemNum(itemid);
    }

    @PutMapping("/setnum")
    public JSONObject setItenNum(@RequestParam("itemid") String itemid,@RequestParam("itemnum") String itemnum) throws IOException {
        return  userInfoService.setItemNum(itemid,itemnum);
    }
}
