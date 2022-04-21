package com.example.pds.config;

import com.example.pds.controllers.profiles.ProfilesService;
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
    private final ProfilesService profileService;

    private final PasswordEncoder encoder;

    @Autowired
    public SecurityConfig(ProfilesService profileService, PasswordEncoder encoder) {
        this.profileService = profileService;
        this.encoder = encoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/users/register","/login", "/forgottenPassword", "/users/forgottenPassword","/swagger-ui.html","/v2/api-docs","/configuration/ui","/swagger-resources/**","/configuration/security","/webjars/**").permitAll()
                .antMatchers("/","/package/getAllMyPackages", "/package/send", "/users/**", "/transaction/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/", "/agent/**","/package/**").hasAnyAuthority("AGENT", "ADMIN")
                .antMatchers("/", "/driver/**", "/vehicle/**") .hasAnyAuthority("DRIVER", "ADMIN")
                .antMatchers("/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), new ObjectMapper()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.profileService))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.profileService)
                .passwordEncoder(this.encoder);
    }
}
