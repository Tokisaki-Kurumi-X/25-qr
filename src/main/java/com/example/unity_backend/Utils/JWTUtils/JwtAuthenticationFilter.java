package com.example.unity_backend.Utils.JWTUtils;


import com.example.unity_backend.Utils.ConfigUtils.Whitelist;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JWTUtil jwtUtil;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final Whitelist whitelist;
    @Autowired
    public JwtAuthenticationFilter(Whitelist whitelist1, JWTUtil util){
        this.whitelist=whitelist1;
        this.jwtUtil=util;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        //Log.showDebug("[shoudnnotfilete]"+path);
        // 只要匹配白名单，就跳过整个过滤器
        boolean allowed =whitelist.getPatterns().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
        //Log.showDebug("[shoudnnotfilete]"+String.valueOf(allowed));
        if(allowed) LogUtil.showDebug("[Filter]In whitelist");
        return allowed;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        //设置以下头信息允许跨域
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        //设置编码
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/plain; charset=UTF-8");
        LogUtil.showDebug("start filter");
        //Log.showDebug(req.toString());
        String header = req.getHeader("Authorization");
        //LogUtil.showDebug("header is \""+header+"\"");
        if (header == null || header.isEmpty()) {
            LogUtil.showDebug("[Filter]required header");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("[Filter]Missing Authorization header");
            return;
        }
        if (header != null) {
            // && header.startsWith("Bearer ")
            //String token = header.substring(7);
            String token=header;
            if(!jwtUtil.isValidToken(token)){
                return;
            }
            //LogUtil.showDebug("token is \""+token+"\"");
            try {
                Claims claims = jwtUtil.parseToken(token);
                String username = claims.getSubject();
                if(username==null){
                    return;
                }
                List<GrantedAuthority> authorities = jwtUtil.getAuthorities(claims);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException e) {
                // token 无效或过期
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("Invalid or expired JWT");
                return;
            }
        }
        LogUtil.showDebug("[filter]用户身份确认完毕！");
        chain.doFilter(req, res);
    }
}
