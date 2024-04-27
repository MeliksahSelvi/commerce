package com.commerce.order.query.service.common.security;

import com.commerce.order.query.service.common.exception.OrderQueryInfraException;
import com.commerce.order.query.service.common.model.UserPrincipal;
import com.commerce.order.query.service.common.rest.dto.ErrorResponse;
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
import java.util.Collections;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userPrincipalAsJson = request.getHeader("userPrincipal");

        if (!StringUtils.hasText(userPrincipalAsJson)) {
            modifyResponseThroughError(response, HttpStatus.UNAUTHORIZED, "You have to have valid token");
        } else {
            UserPrincipal userPrincipal = objectMapper.readValue(userPrincipalAsJson, UserPrincipal.class);
            setAuthenticationToContext(userPrincipal);
            filterChain.doFilter(request, response);
        }
    }

    private void modifyResponseThroughError(HttpServletResponse response, HttpStatus httpStatus, String message) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.getReasonPhrase(), message);

        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (JsonProcessingException e) {
            throw new OrderQueryInfraException("Could not create ErrorResponse json!", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAuthenticationToContext(UserPrincipal userPrincipal) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
