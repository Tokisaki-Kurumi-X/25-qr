package com.example.unity_backend.Controller;

import com.alibaba.fastjson.JSONObject;

import com.example.unity_backend.Entity.User;
import com.example.unity_backend.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
        private RegisterService registerService;
        @Autowired
        public RegisterController(RegisterService registerService1){
                this.registerService=registerService1;
        }
        @PostMapping("/verify/username")
        public JSONObject verifyUsername(@RequestBody JSONObject json){
                return  registerService.verifyUsername(json.getString("username"));
        }
        @PostMapping("/verify/mail")
        public JSONObject verifyMailAddress(@RequestBody JSONObject json){
                return registerService.verifyMailAddress(json.getString("mail"));
        }

        @PostMapping("/new")
        public JSONObject registerNewUser(@RequestBody User user){
                return registerService.registerNewUser(user);
        }
}
