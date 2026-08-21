package com.example.authentication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    public Key getKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String email, String userName, Long referenceId, List<String>roleNames){
      return Jwts.builder().
              setSubject(email).
              claim("userName",userName).
              claim("referenceId",referenceId).
              claim("roleNames",roleNames).
              setIssuedAt(new Date()).
              setExpiration(new Date(System.currentTimeMillis()+1000*60*60)).
              signWith(getKey()).compact();
    }
    public Claims extractClaims(String token){
     return Jwts.parserBuilder().
             setSigningKey(getKey()).
             build().
             parseClaimsJws(token).
             getBody();
    }

    public String extractEmail(String token){
        return extractClaims(token).getSubject();

    }
    public String extractUserName(String token){
        return  extractClaims(token).get("userName", String.class);
    }
    public Long extractReferenceId(String token){
        return  extractClaims(token).get("referenceId", Long.class);
    }
    public List<String>extractRoleNames(String token){
        return extractClaims(token).get("roleNames", List.class);
    }

    public Boolean isTokenValid(String token,String email){
        return extractEmail(token).equals(email)&&! isTokenExpired(token);
    }

    public Boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }




}
