package com.condosaas.api.config;

import com.condosaas.api.security.ApiPermissionMatcher;
import com.condosaas.api.security.ApiRequestPaths;
import com.condosaas.api.security.PermisoCatalog;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PermissionAuthorizationFilter extends OncePerRequestFilter {

    private final ApiPermissionMatcher permissionMatcher;
    private final AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = ApiRequestPaths.resolve(request);
        if (path.startsWith("/api/auth/") || "OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (hasAuthority(auth, "ROLE_" + PermisoCatalog.ROL_ADMIN)) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> required = permissionMatcher.resolve(request);
        if (required.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!hasAuthority(auth, required.get())) {
            if (!response.isCommitted()) {
                accessDeniedHandler.handle(request, response,
                        new AccessDeniedException("Sin permiso: " + required.get()));
            }
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean hasAuthority(Authentication auth, String authority) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals(authority));
    }
}
