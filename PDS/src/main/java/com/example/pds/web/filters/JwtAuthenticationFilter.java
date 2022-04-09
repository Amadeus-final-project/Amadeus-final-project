package com.example.pds.web.filters;

import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.user.userDTO.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final ObjectMapper objectMapper;

    private final String authSecret = "SUPER_DUPER_MEGA_GIGA_SECRET";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDTO user = this.objectMapper.readValue(request.getInputStream(), LoginDTO.class);

            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>() // ROLES -> GRANTED AUTHORITY
                    )
            );
        } catch (IOException ignored) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Profile user = ((Profile) authResult.getPrincipal());

        GrantedAuthority authority = user.getAuthorities()
                .stream()
                .findFirst()
                .orElse(null);

        String authorityString = authority == null ? null : authority.getAuthority();

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .claim("role", authorityString)
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, this.authSecret.getBytes(StandardCharsets.UTF_8))
                .compact();

        response.getWriter()
                .append("{\"token\":" + "\"" + token + "\"}");
    }
}
