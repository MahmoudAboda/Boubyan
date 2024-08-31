/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.util;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class BaseJwtUtils {

    @Value("${jwtSecret.key}")
    protected String jwtSecret;

    @Value("${jwtExpirationInMillis}")
    protected String JWT_TOKEN_VALIDITY; // 5 mins



    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String role = (String) claims.get("role");
        return role;
    }

    public String getFullNameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String fullName = (String) claims.get("fullName");
        return fullName;
    }
    public Integer getUserIdFromToken(String token) {
        if(token.startsWith("Bearer ")) token = token.substring(7);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId;
    }
    public String getEmployeeIdFromToken(String token) {
        if(token.startsWith("Bearer ")) token = token.substring(7);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.get("employeeId")!=null?claims.get("employeeId").toString():null;
    }
    public String getStudentIdFromToken(String token) {
        if(token.startsWith("Bearer ")) token = token.substring(7);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.get("studentId")!=null?claims.get("studentId").toString():null;
    }


    //retrieve expiration date from jwt token
    protected Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    protected Claims getAllClaimsFromToken(String token) {
        if(token.contains("Bearer "))
            token = token.substring(7);
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
    protected Map<String, Object> parseToken(String token){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] parts = token.split("\\."); // Splitting header, payload and signature
        Gson g = new Gson();
        return g.fromJson(
                new String(decoder.decode(parts[1]))
                , Map.class);
    }

}
