package com.example.Photograph.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.Instant;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.expiration}")
    private int expiratioTime;

    @Value("${jwt.secret}")
    private String secret;

    public String generateJWT(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date.from(Instant.now().plusMillis(expiratioTime)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Date getExpirationDateFromToken(String token){
        Claims claims = (Claims) Jwts.parser()
                .setSigningKey(secret)
                .parse(token)
                .getBody();
        return claims.getExpiration();
    }

    public String getUsernameFromToken(String token){
        Claims claims = (Claims) Jwts.parser()
                .setSigningKey(secret)
                .parse(token)
                .getBody();
        return claims.getSubject();
    }
}
