package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;

import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Entity.VerifyCode;
import com.example.unity_backend.Service.RegisterService;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/register")
public class RegisterController {
        private RegisterService registerService;
        @Autowired
        public RegisterController(RegisterService registerService1){
                this.registerService=registerService1;
        }

        @PostMapping("/verify/username")
        public JSONObject verifyUsername(@RequestBody User user) throws IOException {
                return  registerService.verifyUsername(user);
        }
        @PostMapping("/verify/mail")
        public JSONObject verifyMailAddress(@RequestBody JSONObject json) throws IOException {
                return registerService.verifyMailAddress(json.getString("mail"));
        }

        @PostMapping("/new")
        public JSONObject registerNewUser(@RequestBody User user) throws IOException {
                return registerService.registerNewUser(user);
        }

        @PostMapping("/mail/code")
        public JSONObject sendCode(@RequestBody VerifyCode verifyCode) throws IOException {
                return registerService.sendCode(verifyCode);
        }

        @PostMapping("/verify/code")
        public JSONObject verifyMailAddressbyCode(@RequestBody VerifyCode verifyCode) throws IOException {
                //LogUtil.showDebug(verifyCode.toString());
                return registerService.verifyMailAddressbyCode(verifyCode);
        }
}
