package com.auth.controller;

import com.auth.dto.AuthRequest;
import com.auth.dto.AuthResponse;
import com.auth.model.User;
import com.auth.service.JwtService;
import com.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token, "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Invalid credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        try {
            User user = userService.registerUser(request.getUsername(), request.getPassword());
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
            String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token, "Registration successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                String username = jwtService.extractUsername(jwt);
                UserDetails userDetails = userService.loadUserByUsername(username);
                
                if (jwtService.validateToken(jwt, userDetails)) {
                    return ResponseEntity.ok("Token is valid for user: " + username);
                }
            }
            return ResponseEntity.badRequest().body("Invalid token");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }
}
