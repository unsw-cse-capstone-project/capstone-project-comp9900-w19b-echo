package com.echo.backend.controller;

import com.echo.backend.dto.LoginRequest;
import com.echo.backend.dto.RegisterRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import com.echo.backend.dto.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/sign-in")
    public User login(@RequestBody LoginRequest loginRequest) {
        String token = getJWTToken(loginRequest.getEmail());
        User user = new User();
        user.setEmail(loginRequest.getEmail());
        user.setToken(token);
        return user;
    }

    @PostMapping("/sign-up")
    public User register(@RequestBody RegisterRequest registerRequest) {
        String token = getJWTToken(registerRequest.getEmail());
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setToken(token);
        return user;
    }

    private String getJWTToken(String username) {
        String secretKey = "OlpSecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("OlpJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
}
