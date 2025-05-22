package com.example.electroplan_backend.filters;

import com.example.electroplan_backend.services.JwtService;
import com.example.electroplan_backend.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    // List of protected URLs
    private static final List<String> PROTECTED_URLS = List.of(
            "/example",
            "/auth/logout",
            "/users/phone",
            "/users/change-password",
            "/users/profile",
            "/users/email",
            "/projects/**",
            "/project-images/add",
            "/project-images/add-multiple",
            "/project-images/get-all",
            "/user-images/upload" ,
            "/user-banners/upload",
            "/user-banners/delete",
            "/user-images/delete"
    );
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // Skip unprotected URLs
        if (!isProtected(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get token
        var authHeader = request.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Denied: Token is missing or invalid");
            return;
        }

        // Get user from token
        String username;
        String jwt;

        try {
            jwt = authHeader.substring(BEARER_PREFIX.length());
            username = jwtService.extractUserName(jwt);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Denied: " + e.getMessage());
            return;
        }

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            try {
                userDetails = userService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access Denied: Invalid token or user not found");
                return;
            }

            // If token is valid, authenticate user
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access Denied: Invalid token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isProtected(String requestURI) {
        return PROTECTED_URLS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }
}
