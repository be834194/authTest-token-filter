package com.test.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.auth.JwtProducer;
import com.test.auth.entity.UserDetailsImpl;
import com.test.auth.form.AccountForm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtProducer jwtProducer;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtProducer jwtProducer) {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        setUsernameParameter("userName");
        setPasswordParameter("password");

        this.jwtProducer = jwtProducer;
        this.setAuthenticationManager(authenticationManager);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            // requestパラメータからユーザ情報を読み取る
            AccountForm accountForm = new ObjectMapper().readValue(req.getInputStream(), AccountForm.class);

            return this.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(accountForm.getUserName(), accountForm.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = jwtProducer.createToken(((UserDetailsImpl)auth.getPrincipal()).getUsername());
        res.addHeader("x-auth-token",token);
    }
}
