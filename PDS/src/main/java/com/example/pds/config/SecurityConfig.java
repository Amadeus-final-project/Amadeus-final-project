package com.example.pds.config;

import com.example.pds.model.user.UserService;
import com.example.pds.web.filters.JwtAuthenticationFilter;
import com.example.pds.web.filters.JwtAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    private final PasswordEncoder encoder;

    @Autowired
    public SecurityConfig(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("**").permitAll()
                    //.antMatchers("/**", "/users/register", "/users/login", "/swagger**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), new ObjectMapper()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userService))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userService)
                .passwordEncoder(this.encoder);
    }
}
