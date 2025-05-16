package com.learningmanagementsystem.UserService.service.serviceImpl;


import com.learningmanagementsystem.UserService.exception.CustomizedBadCredentialsException;
import com.learningmanagementsystem.UserService.service.JwtService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secretKey}")
    private String secret;


    @Value("${jwt.expiration}")
    private int expiration;

    @Override
    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).
                setExpiration(new Date(new Date().getTime() + expiration)).signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    @Override
    public Boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken);
            return true;
        }catch (SignatureException exception){
            throw new CustomizedBadCredentialsException("Invalid jwt token");
        }catch (MalformedJwtException exception){
            throw new CustomizedBadCredentialsException("Invalid jwt token");
        }catch (ExpiredJwtException exception){
            throw new CustomizedBadCredentialsException("Jwt token has expired");
        }catch (UnsupportedJwtException exception){
            throw new CustomizedBadCredentialsException("Invalid jwt token");
        }
    }

    @Override
    public long getTokenExpiration() {
        return new Date().getTime() + expiration;
    }


}
