package com.example.pds.web.filters;

import com.example.pds.profiles.Profile;
import com.example.pds.profiles.ProfilesService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final ProfilesService profilesService;

    private final String authSecret = "SUPER_DUPER_MEGA_GIGA_SECRET";

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, ProfilesService profilesService) {
        super(authenticationManager);
        this.profilesService = profilesService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = this.getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

        if(token != null) {
            String processedToken = token.replace("Bearer ", "");

            String username = Jwts.parser()
                    .setSigningKey(this.authSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(processedToken)
                    .getBody()
                    .getSubject();

            if(username != null) {
                Profile user = (Profile) this.profilesService.loadUserByUsername(username);

                Map<String, Object> credentials = new LinkedHashMap<>();

                credentials.put("id", user.getId());
                credentials.put("email", user.getEmail());

                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        credentials,
                        user.getAuthorities()
                );
            }
        }

        return usernamePasswordAuthenticationToken;
    }
}
