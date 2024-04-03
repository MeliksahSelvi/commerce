package com.commerce.user.service.common.security.filter;

import com.commerce.user.service.common.exception.UserDomainException;
import com.commerce.user.service.common.exception.UserInfraException;
import com.commerce.user.service.common.rest.dto.ErrorResponse;
import com.commerce.user.service.common.security.principal.UserPrincipal;
import com.commerce.user.service.common.security.util.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> openApiEndpoints = List.of("/api/v1/auth/login", "/api/v1/auth/register", "/swagger-ui/**",
            "/swagger-ui.html", "/v3/api-docs/**");

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(TokenProvider tokenProvider, ObjectMapper objectMapper) {
        this.tokenProvider = tokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isServletPathIncludeOneOfSecureEndpoints(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        if (!StringUtils.hasText(token) || tokenProvider.isTokenExpired(token)) {
            modifyResponseThroughError(response, HttpStatus.UNAUTHORIZED, "You have to have valid token");
            return;
        }

        UserPrincipal userPrincipal = getUserPrincipal(token);

        if (userPrincipal == null) {
            modifyResponseThroughError(response, HttpStatus.UNAUTHORIZED, "You have to have valid token");
            return;
        }

        setAuthenticationToContext(userPrincipal);

        filterChain.doFilter(request, response);
    }

    private boolean isServletPathIncludeOneOfSecureEndpoints(HttpServletRequest request) {
        return openApiEndpoints.stream()
                .anyMatch(s -> request.getServletPath().startsWith(s));
    }

    private String getToken(HttpServletRequest request) {
        String fullToken = request.getHeader("Authorization");

        String token = null;
        if (StringUtils.hasText(fullToken)) {
            String bearerStr = "Bearer ";

            if (fullToken.startsWith(bearerStr)) {
                token = fullToken.substring(bearerStr.length());
            }
        }
        return token;
    }

    private UserPrincipal getUserPrincipal(String token) {
        String userPrincipalFromToken = tokenProvider.exractUserPrincipalFromToken(token);
        try {
            return objectMapper.readValue(userPrincipalFromToken, UserPrincipal.class);
        } catch (JsonProcessingException e) {
            throw new UserDomainException("Could not read UserPrincipal object!", e);
        }
    }

    private void modifyResponseThroughError(HttpServletResponse response, HttpStatus httpStatus, String message) {
        SecurityContextHolder.clearContext();
        returnErrorResponse(response, httpStatus, message);
    }

    private void returnErrorResponse(HttpServletResponse response, HttpStatus httpStatus, String message) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.getReasonPhrase(), message);

        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        try {
            OutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, errorResponse);
            outputStream.flush();
        } catch (JsonProcessingException e) {
            throw new UserInfraException("Could not create ErrorResponse json!", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAuthenticationToContext(UserPrincipal userPrincipal) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
