package com.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/secured")
    public Map<String, Object> getSecuredData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a secured API endpoint");
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("timestamp", System.currentTimeMillis());
        
        return response;
    }

    @GetMapping("/public")
    public String getPublicData() {
        return "This is a public API endpoint - no authentication required";
    }
}
