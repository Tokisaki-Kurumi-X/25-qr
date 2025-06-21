package com.example.unity_backend.Utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
    public static void showDebug(String str){
        System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+"]"+str);
    }
}
