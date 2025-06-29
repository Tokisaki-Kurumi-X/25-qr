package com.example.unity_backend.Dao.Mybatis;

import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class Mybatis{
    private SqlSession sqlSession;
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    public void closeSqlSession() {
        if (this.sqlSession != null) {
            this.sqlSession.commit();  // 提交事务
            this.sqlSession.close();   // 关闭 session
        }
    }

    @Value("${mybatis.resource}")
    private  String resource;

    public void initMybatis() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //读配置
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //打开会话
        this.sqlSession=sqlSessionFactory.openSession();
        //LogUtil.showDebug("mybatis.class: openSession successfully");
    }

}
