/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.util;

import com.google.gson.Gson;
import com.oschool.student.enumeration.AppClient;
import com.oschool.student.enumeration.Role;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Component
public class JwtUtils extends BaseJwtUtils {
    public String generateJwtTokenForStudent(String subject, String userName, Set<? extends GrantedAuthority> authorities,
                                             String role, Long userId ,
                                             Long StudentId

    ) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("clientId", AppClient.MS_STUDENT.getId())
                .claim("role", Role.valueOf(role).getId())
                .claim("authorities", authorities)
                .claim("userId", userId)
                .claim("name", userName)
                .claim("studentId", StudentId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_TOKEN_VALIDITY)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String doGenerateRefreshTokenForStudent(String subject, String  userName, Set<? extends GrantedAuthority> authorities,
                                                   String role, Long userId,
                                                      Long StudentId
    ) {
        try {


        return Jwts.builder()
                .setSubject(subject)
                .claim("clientId", AppClient.MS_STUDENT.getId())
                .claim("role", Role.valueOf(role).getId())
                .claim("authorities", authorities)
                .claim("userId", userId)
                .claim("name", userName)
                .claim("studentId", StudentId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_TOKEN_VALIDITY)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        }
        catch (Exception e){}
            throw new RuntimeException("dsdsd");
    }

    //retrieve username from jwt token
    @Override
    public String getUsernameFromToken(String token) {
        return super.getUsernameFromToken(token);
    }
    @Override
    public String getRoleFromToken(String token) {
        return super.getRoleFromToken(token);
    }
    @Override
    public String getFullNameFromToken(String token) {
        return super.getFullNameFromToken(token);
    }
    @Override
    public Integer getUserIdFromToken(String token) {
        return super.getUserIdFromToken(token);
    }
    @Override
    public String getEmployeeIdFromToken(String token) {
        return super.getEmployeeIdFromToken(token);
    }
    @Override
    public String getStudentIdFromToken(String token) {
        return super.getStudentIdFromToken(token);
    }
    //retrieve expiration date from jwt token
    @Override
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    @Override
    public Claims getAllClaimsFromToken(String token) {
        if(token.contains("Bearer "))
            token = token.substring(7);
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
    @Override
    public Map<String, Object> parseToken(String token){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] parts = token.split("\\."); // Splitting header, payload and signature
        Gson g = new Gson();
        return g.fromJson(
                new String(decoder.decode(parts[1]))
                , Map.class);
    }
}
