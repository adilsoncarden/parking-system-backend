package com.condosaas.api.config;

import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.rol.repository.RolRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Alinea la base de datos con la Especificación Técnica V6 al arrancar.
 *
 * Se hace en código (y no solo con los scripts de {@code spring.sql.init}) porque esos
 * scripts corren con {@code continue-on-error=true} y pueden fallar en silencio; aquí
 * queda registrado en el log y es idempotente: si ya se aplicó, no hace nada.
 *
 * Corre antes que {@link PermissionDataInitializer} para que los permisos se asignen
 * sobre los nombres de rol ya actualizados.
 */
@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class SpecV6Initializer implements ApplicationRunner {

    private final RolRepository rolRepository;
    private final EntityManager entityManager;

    // Nombres previos -> nombres de la especificación V6.
    private static final Map<String, String> RENOMBRES = Map.of(
            "RESIDENTE", "PROPIETARIO",
            "PORTERO", "AGENTE_SEGURIDAD");

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        renombrarRoles();
        permitirVehiculosSinDueno();
    }

    /** Renombra los roles al nombre exacto del spec, conservando su id. */
    private void renombrarRoles() {
        RENOMBRES.forEach((anterior, nuevo) -> {
            if (rolRepository.findByNombre(nuevo).isPresent()) {
                return; // ya renombrado
            }
            rolRepository.findByNombre(anterior).ifPresent(rol -> {
                rol.setNombre(nuevo);
                rolRepository.save(rol);
                log.info("Rol renombrado segun spec V6: {} -> {}", anterior, nuevo);
            });
        });
    }

    /**
     * Los vehículos de VISITANTE no tienen dueño residente, así que id_usuario debe
     * admitir null. Hibernate (ddl-auto=update) nunca quita un NOT NULL existente.
     */
    private void permitirVehiculosSinDueno() {
        try {
            entityManager
                    .createNativeQuery("ALTER TABLE vehiculo ALTER COLUMN id_usuario DROP NOT NULL")
                    .executeUpdate();
            log.info("vehiculo.id_usuario ahora admite null (vehiculos de visitante)");
        } catch (Exception ex) {
            // Ya estaba nullable o el motor no lo soporta: no es un fallo de arranque.
            log.warn("No se pudo aplicar DROP NOT NULL en vehiculo.id_usuario: {}", ex.getMessage());
        }
    }
}
