package com.example.block7crudvalidation.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtToken {
    public static final String SECRET_KEY = "Prueba";

    public String generateToken(String usuario, String role) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);

        String token = Jwts.builder()
                .setSubject(usuario)
                .claim("authorities", grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();

        return "Bearer " + token;
    }
}
