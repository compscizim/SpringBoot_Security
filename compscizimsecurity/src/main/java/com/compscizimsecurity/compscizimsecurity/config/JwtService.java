package com.compscizimsecurity.compscizimsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String secret="rQCVYRWC0N1l2aoaVezHun1F9IJqnqq10f26FTxvc9L5e9GXM0My3v2mgod3wbu";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public String generateToken(UserDetails userDetails ){
        return generateToken(userDetails, new HashMap<>());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpire(token));

    }

    private boolean isTokenExpire(String token) {
        return extractExpirationTime(token).before(new Date());
    }

    private Date extractExpirationTime(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000 *60 *24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private <T> T extractClaim(String token, Function<Claims , T> claimDecoder){
        Claims claim =extraAllClaims(token);
        return claimDecoder.apply(claim);
    }

    private Claims extraAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[]  keybyte = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keybyte);
    }
}
