package com.commerce.user.service.adapters.user.security;

import com.commerce.user.service.common.exception.UserDomainException;
import com.commerce.user.service.common.security.principal.UserPrincipal;
import com.commerce.user.service.user.model.User;
import com.commerce.user.service.user.port.security.TokenGeneratePort;
import com.commerce.user.service.user.usecase.JwtToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@Service
public class TokenGenerateAdapter implements TokenGeneratePort {

    private static final Logger logger = LoggerFactory.getLogger(TokenGenerateAdapter.class);

    @Value("${commerce.jwt.security.key}")
    private String TOKEN_KEY;

    @Value("${commerce.jwt.security.expire-time}")
    private Long EXPIRE_TIME;

    private final ObjectMapper objectMapper;

    public TokenGenerateAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public JwtToken generateToken(User user) {

        Map<String, Object> claims = buildClaims(user);

        UserPrincipal userPrincipal = buildPrincipal(user);

        String userPrincipalAsJson = convertDataToJson(userPrincipal);

        String token = createToken(claims, userPrincipalAsJson);
        logger.info("Created token for userId: {}", user.getId());

        setAuthenticationToContext(userPrincipal);
        return new JwtToken(user.getId(), token, EXPIRE_TIME);
    }

    private Map<String, Object> buildClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        return claims;
    }

    private UserPrincipal buildPrincipal(User user) {
        return new UserPrincipal(user.getEmail(), user.getId(), user.getRoleId());
    }

    private String convertDataToJson(UserPrincipal userPrincipal) {
        try {
            return objectMapper.writeValueAsString(userPrincipal);
        } catch (JsonProcessingException e) {
            throw new UserDomainException("Could not create UserPrincipal object", e);
        }
    }

    private String createToken(Map<String, Object> claims, String userPrincipalAsJson) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipalAsJson)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
                .compact();
    }

    private void setAuthenticationToContext(UserPrincipal userPrincipal) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
