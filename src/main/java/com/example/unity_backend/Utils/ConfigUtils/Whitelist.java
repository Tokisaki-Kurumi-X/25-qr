package com.example.unity_backend.Utils.ConfigUtils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Whitelist {
    private  final List<String> patterns = Arrays.asList(
            "/login",
            "/registered",
            "/api/product/**",
            "/static/**",
            "/webjars/**",
            "/login.html",
            "/main.html",
            "/test/**",
            // 如果后续有更多路径，只要加到这里就行
            "/public/**",
            "/health"
    );

    public List<String> getPatterns() {
        return patterns;
    }
}
