package com.commerce.user.service.common.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@Component
public class TokenProvider {

    @Value("${commerce.jwt.security.key}")
    private String TOKEN_KEY;

    public boolean isTokenExpired(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        Date expirationDate = claimsJws.getBody().getExpiration();
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    public String exractUserPrincipalFromToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return claimsJws.getBody().getSubject();
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token);
    }
}
