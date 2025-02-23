package com.test.auth.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.test.auth.JwtProducer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthorizeFilter extends OncePerRequestFilter {

    private JwtProducer jwtProducer;

    private RequestMatcher anyMatcher = RequestMatchers.anyOf(new AntPathRequestMatcher("/login", "POST"),
                                                              new AntPathRequestMatcher("/account", "POST"));

    public AuthorizeFilter(JwtProducer jwtProducer) {
        this.jwtProducer = jwtProducer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        if (!anyMatcher.matches(request)) {
            // headersのkeyを指定してトークンを取得
            String xAuthToken = request.getHeader("x-auth-token");
            if (xAuthToken == null || !xAuthToken.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            // tokenの検証と認証
            DecodedJWT decodedJWT = jwtProducer.verifyToken(xAuthToken.substring(7));
            String username = decodedJWT.getClaim("userName").toString();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
        }
        filterChain.doFilter(request, response);
    }
}
