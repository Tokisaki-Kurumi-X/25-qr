package com.example.unity_backend.Interceptor;


import com.example.unity_backend.Utils.ConfigUtils.Whitelist;
import com.example.unity_backend.Utils.JWTUtils.JWTUtil;
import com.example.unity_backend.Utils.LogUtils.LogUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final Whitelist WHITE_LIST;

    @Autowired
    public AuthInterceptor(Whitelist whitelist) {
        this.WHITE_LIST = whitelist;
    }

    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        LogUtil.showDebug("[Interceptor] start");
        HttpServletRequest request_cache=request;
       // Log.showDebug("[Interceptor]ip is "+ IPUtil.getIpAddress(request_cache));
        String path = request.getServletPath().toString();// 用 getRequestURI() 获取完整路径
        //System.out.println("Request URI: " + path);
        boolean allowed =WHITE_LIST.getPatterns().stream()
                .anyMatch(pattern -> matcher.match(pattern, path));
        //Log.showDebug(String.valueOf(allowed));
        if(allowed){ return  true;}
//        // 获取客户端传递的 token [Authorization]
//        Authentication authentication = SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//        Log.showDebug("[Interceptor:line42]"+authentication.toString());
//        // 如果没登录或匿名
//        if (authentication == null || !authentication.isAuthenticated()) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }

//        // 拿到角色列表
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        // 举例：检查是否有 ROLE_ADMIN
//        boolean isAdmin = authorities.stream()
//                .anyMatch(ga -> "ROLE_ADMIN".equals(ga.getAuthority()));
//
//        if (request.getServletPath().startsWith("/admin") && !isAdmin) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write("需要管理员权限");
//            return false;
//        }
        // 获取客户端传递的 token [Authorization]
        String token = request.getHeader("Authorization");
        //LogUtil.showDebug("[Interceptor]"+token);
        if (token == null || token.isEmpty()) {
            // 客户端没有带 token，返回401未授权
          //  Log.showDebug("[Interceptor]token requested");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User identity id shoudn't be null");
            //response.getWriter().write("111");
            return false; // 拦截请求
        }
        //Log.showDebug("[Intercepter]拦截器开始校验Token有效性");
        // 校验 token 合法性
        JWTUtil util = new JWTUtil();
        boolean isValid = util.isValidToken(token);

        if (!isValid) {
            // 如果 token 无效，返回 401 错误
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("222");
            return false; // 拦截请求
        }


       String username = util.getUsernameFromToken(token);
//        if(username==null){
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("No subject");
//            return false;
//        }
        // 或者将其存入 SecurityContext 供 Spring Security 使用
        Authentication authentication = new UsernamePasswordAuthenticationToken( username, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true; // token 合法，继续执行
    }
}
