package com.condosaas.api.security;

import com.condosaas.api.exception.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Resuelve el contexto del usuario autenticado para el aislamiento multi-condominio.
 *
 * - Superadmin (rol ADMIN): condominio nulo, ve y gestiona todo el sistema.
 * - Admin de condominio: ligado a un condominio (claim en el JWT, transportado como
 *   authority "CONDO_<id>"); queda "scoped" a ese condominio.
 * - Cualquier otro usuario sin condominio: no se le aplica filtro (comportamiento previo).
 */
@Component
public class CurrentUser {

    public static final String CONDO_AUTHORITY_PREFIX = "CONDO_";

    private Authentication auth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isSuperAdmin() {
        Authentication a = auth();
        if (a == null) {
            return false;
        }
        return a.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(x -> x.equals("ROLE_" + PermisoCatalog.ROL_ADMIN));
    }

    public Long condominioId() {
        Authentication a = auth();
        if (a == null) {
            return null;
        }
        return a.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(x -> x.startsWith(CONDO_AUTHORITY_PREFIX))
                .map(x -> {
                    try {
                        return Long.valueOf(x.substring(CONDO_AUTHORITY_PREFIX.length()));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(x -> x != null)
                .findFirst()
                .orElse(null);
    }

    /** True si al usuario hay que limitarlo a un único condominio. */
    public boolean isScoped() {
        return !isSuperAdmin() && condominioId() != null;
    }

    /** Para filtros de listado: el superadmin usa lo pedido; el scoped queda forzado al suyo. */
    public Long resolveFilter(Long requested) {
        return isScoped() ? condominioId() : requested;
    }

    /** Verifica que el usuario pueda tocar ese condominio; si no, 403. */
    public void assertCondominio(Long condominioId) {
        if (isScoped() && !condominioId().equals(condominioId)) {
            throw new ForbiddenException("No tienes acceso a este condominio");
        }
    }
}
