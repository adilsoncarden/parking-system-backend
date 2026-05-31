package com.condosaas.api.config;

import com.condosaas.api.security.ApiPermissionMatcher;
import com.condosaas.api.security.PermisoCatalog;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PermissionAuthorizationFilter extends OncePerRequestFilter {

    private final ApiPermissionMatcher permissionMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        if (uri.startsWith("/api/auth/")) {
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
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sin permiso: " + required.get());
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
