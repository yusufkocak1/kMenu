package com.kocak.authserver.controller;

import com.kocak.authserver.model.User;
import com.kocak.authserver.service.JwtService;
import com.kocak.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/public")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        User foundUser = userService.findByEmail(user.getEmail());
        if (foundUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(user.getPassword(),foundUser.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = jwtService.generateToken(foundUser);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("name",foundUser.getName());
        tokenMap.put("surname",foundUser.getSurname());
        tokenMap.put("userId",foundUser.getId());
        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestParam("token") String token) {
        boolean isValid = jwtService.validateToken(token);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}
