package com.example.unity_backend.Utils.SqlUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SqlGenerator {
    @Value("${tableName.testPath}")
    private String testTable;

     /*
        命名规范：generate+函数名+Sql
        结构规范：调用源+函数体

        新建步骤:
        1.yml配置表名
        2.新建private表名变量并且注入
        3.拼函数
     *///编写规范
     //***************模板***************

        /*
        call func:Dao/TestDao/TestDao/testPath
        */

         public  String generatetestPath(){
             return  "select * from "+testTable;
         }

     //***************模板***************




}
