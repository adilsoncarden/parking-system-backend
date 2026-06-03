package com.condosaas.api.security;

import jakarta.servlet.http.HttpServletRequest;

public final class ApiRequestPaths {

    private ApiRequestPaths() {
    }

    public static String resolve(HttpServletRequest request) {
        String path = request.getServletPath();
        if (path == null || path.isBlank()) {
            path = request.getRequestURI();
        }
        String contextPath = request.getContextPath();
        if (contextPath != null && !contextPath.isBlank() && path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        return path;
    }
}
