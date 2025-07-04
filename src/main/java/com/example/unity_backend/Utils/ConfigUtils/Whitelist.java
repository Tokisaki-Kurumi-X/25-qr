package com.example.unity_backend.Utils.ConfigUtils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Whitelist {
    private  final List<String> patterns = Arrays.asList(
            "/login/**",
            "/register/**",
            "/static/*",
            "/webjars/**",
            "/login.html",
            "/main.html",
            "/test/**",
            // 如果后续有更多路径，只要加到这里就行
            "/public/**",
            "/register.html",
            "/test.html",
            "/config.js",
            "/index.html",
            "/level.html",
            "/Level/Level1.html",
            "/Level/Level2.html",
            "/record.html",
            "/activity.html",
            "/store.html",
            "/warehouse.html",
            "/userinfo.html",
            "/log.html",
            "/reset.html",
            "/reset/**",
            "/reset_main.html"
    );

    public List<String> getPatterns() {
        return patterns;
    }
}
