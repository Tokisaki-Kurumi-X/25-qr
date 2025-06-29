package com.example.unity_backend.Security;


import com.example.unity_backend.Utils.ConfigUtils.Whitelist;
import com.example.unity_backend.Utils.JWTUtils.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// 开启 @PreAuthorize、@PostAuthorize 等注解支持
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationFilter jwtFilter;

    private String[] patterns;
    @Autowired
    public SecurityConfig(JwtAuthenticationFilter filter,Whitelist whitelist1){
        this.jwtFilter=filter;
        this.patterns=whitelist1.getPatterns().toArray(new String[0]);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 登录接口、静态资源放行
                .antMatchers(patterns).permitAll()
//                // 根据 Authority（无 ROLE_ 前缀）做权限控制
//                .antMatchers("/page1/**").hasAuthority("ROLE_LEVEL1")
//                .antMatchers("/page2/**").hasAuthority("ROLE_LEVEL2")
//                .antMatchers("/page3/**").hasAuthority("ROLE_LEVEL3")
                .antMatchers("/admin").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                // 把 JWT 过滤器加到 Spring Security 过滤链
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }



}
