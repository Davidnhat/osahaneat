package com.daviaNhat.osahaneat.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component //giúp tái sử dụng
public class JwtUtilsHelper {

    //gọi và lấy giá trị của key mã hóa
    @Value("${jwt.privateKey}")
    private String privateKey;

    public String generateToken(String data){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
        String jws = Jwts.builder().subject(data).signWith(key).compact();
        return jws;
    }
    
    public boolean verifyToken(String token){
        try{
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token);

//            jws = Jwts.parser()
//                    .keyLocator(key)
//                    .build()
//                    .parseSignedClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
