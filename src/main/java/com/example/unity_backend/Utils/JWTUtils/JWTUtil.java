package com.example.unity_backend.Utils.JWTUtils;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Dao.UserInfo.UserInfoDao;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
    public class JWTUtil {

        //设置token的有效期：一天
        private static final long TOKEN_EXPIRE = 24*60*60*1000;
        //设置一个密钥\私钥,为了安全需要定期更换
        //对称密钥
        private static final String TOKEN_SECRET_KEY = "nfjkfjslljsgjoajjfoisjfsjlkjfskl";
        @Autowired
        private UserInfoDao userInfoDao;

        public JSONObject generateJson(String username,String audience){
            JSONObject json=new JSONObject();
            json.put("Username",username);
            json.put("Audience",audience);
            return json;
        }

        //未指定实体，通用接口
        public  String generateToken(JSONObject json) throws Exception {
            //****测试用数据开始
            String username="qianhun";//username是发起人
            //实际从json获取数据
            //String **=json.getString()
            //此处应从数据库或者Redis查询
            List<String> roles=new ArrayList<>();
            //roles.add("ROLE_ADMIN");
            roles.add("ROLE_USER");

            Claims claim= Jwts.claims().setSubject(json.getString("Username"));
            claim.put("roles",roles);
            //****测试用数据结束
            Date now = new Date();
            long expireTime = now.getTime() + TOKEN_EXPIRE;
            Date exp = new Date(expireTime);

            // 生成 EC 密钥对
//            KeyPair keyPair = KeyUtil.generateECKeyPair();
//            PrivateKey privateKey = keyPair.getPrivate();

            String token = Jwts.builder()
                    .setIssuedAt(now)
                    .setExpiration(exp)
                    .setClaims(claim)
                    //.setSubject(json.getString("account"))//主体
                    .setAudience(json.getString("Audience"))//平台
                    .setIssuer("QIANHUN")
                    .claim("server_name","qianhun")
                    //.claim("roles","Admin")
                    .signWith(SignatureAlgorithm.HS256,TOKEN_SECRET_KEY)
                    .compact();
            LogUtil.showDebug(token);
            return token;
        }

        public  boolean isValidToken(String token) {
            //showDebug(token);
            try {
                // 解析 Token 并验证其有效性
                parseToken(token);
                               //*********
                               //.getbody(),用Claims承接，可以获得payload部分
                               //*********
                // 如果解析成功，则认为 token 有效
                //showDebug("claims is "+claims.toString());
                LogUtil.showDebug("Token验证成功");
                return true;
            } catch (ExpiredJwtException e) {
                // 捕获 Token 过期异常，返回 false
                System.out.println("Token 已过期");
                return false;
            } catch (UnsupportedJwtException e) {
                // 捕获不支持的 token 异常，返回 false
                System.out.println("不支持的 Token");
                return false;
            } catch (MalformedJwtException e) {
                // 捕获非法格式的 token 异常，返回 false
                System.out.println("非法 Token 格式");
                return false;
            } catch (SignatureException e) {
                // 捕获签名不合法的异常，返回 false
                System.out.println("Token 签名不合法");
                return false;
            } catch (IllegalArgumentException e) {
                // 捕获非法参数异常，返回 false
                System.out.println("非法 Token 参数");
                return false;
            }
        }

        /** 解析并校验 Token**/
        //需要先验证过，有效才解析
        public Claims parseToken(String token) {
            return Jwts.parser()
                    .setSigningKey(TOKEN_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }
        //配合上面的解析函数使用

        public List<GrantedAuthority> getAuthorities(Claims claims) {
            List<String> roles = (List<String>) claims.get("roles");
            //LogUtil.showDebug("roles is"+roles);
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    //可加入不同的getter


    public String getUsernameFromToken(String token) {
            String username;
            Claims claims=parseToken(token);
            username=claims.getSubject();
            return username;
    }

    }
