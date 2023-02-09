package com.portfolio.trading.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT 토큰으로 인증하고 SecurityContextHolder에 추가하는 필터를 설정하는 클래스
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String TOKEN_PREFIX = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(parseToken(token))) {
            Authentication authentication = jwtTokenProvider.getAuthentication(parseToken(token));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String parseToken(String token) {
        // 토큰 정보에서 토큰 타입(Bearer) 제외하고 추출
        if (token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length()).trim();
        }
        return token;
    }
}
