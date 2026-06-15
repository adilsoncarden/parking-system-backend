package com.condosaas.api.module.seed.controller;

import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.estacionamiento.model.EstadoOcupacion;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import com.condosaas.api.module.torre.model.Torre;
import com.condosaas.api.module.torre.repository.TorreRepository;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import com.condosaas.api.module.vehiculo.model.EstadoVehiculo;
import com.condosaas.api.module.vehiculo.model.Vehiculo;
import com.condosaas.api.module.vehiculo.repository.VehiculoRepository;
import com.condosaas.api.module.zona_estacionamiento.model.EstadoZonaEstacionamiento;
import com.condosaas.api.module.zona_estacionamiento.model.ZonaEstacionamiento;
import com.condosaas.api.module.zona_estacionamiento.repository.ZonaEstacionamientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utilidad de poblamiento de datos de demo. Genera vehículos realistas asignados
 * a residentes (usuarios con apartamento), sin duplicar los que ya tienen vehículo.
 * Protegido a nivel admin (ver ApiPermissionMatcher).
 */
@RestController
@RequestMapping("/api/seed")
@RequiredArgsConstructor
public class SeedController {

    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;
    private final CondominioRepository condominioRepository;
    private final TorreRepository torreRepository;
    private final ZonaEstacionamientoRepository zonaEstacionamientoRepository;
    private final EstacionamientoRepository estacionamientoRepository;

    private static final String[] MARCAS = {
            "Toyota", "Nissan", "Hyundai", "Kia", "Chevrolet", "Ford",
            "Mazda", "Volkswagen", "Honda", "Renault", "Suzuki"
    };
    private static final String[] MODELOS = {
            "Sedán", "Hatchback", "SUV", "Pickup", "Crossover", "Coupé"
    };
    private static final String[] COLORES = {
            "Blanco", "Negro", "Gris", "Plateado", "Rojo", "Azul"
    };
    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @PostMapping("/vehiculos")
    @Transactional
    public ResponseEntity<Map<String, Object>> seedVehiculos(
            @RequestParam(defaultValue = "0.75") double fraccion,
            @RequestParam(defaultValue = "40") int topePorCondominio) {

        Random rnd = new Random();

        // Placas ya usadas (para no duplicar)
        Set<String> placasUsadas = vehiculoRepository.findAll().stream()
                .map(Vehiculo::getPlaca)
                .collect(Collectors.toCollection(HashSet::new));

        // Usuarios que ya tienen vehículo (no volver a asignarles)
        Set<Long> conVehiculo = vehiculoRepository.findAll().stream()
                .filter(v -> v.getUsuario() != null)
                .map(v -> v.getUsuario().getId())
                .collect(Collectors.toSet());

        // Candidatos: residentes con apartamento, sin vehículo, agrupados por condominio
        Map<Long, List<Usuario>> porCondominio = new HashMap<>();
        for (Usuario u : usuarioRepository.findAllWithRol()) {
            if (u.getApartamento() == null) continue;
            if (conVehiculo.contains(u.getId())) continue;
            Long condoId = condominioId(u);
            if (condoId == null) continue;
            porCondominio.computeIfAbsent(condoId, k -> new ArrayList<>()).add(u);
        }

        List<Vehiculo> nuevos = new ArrayList<>();
        for (List<Usuario> lista : porCondominio.values()) {
            Collections.shuffle(lista, rnd);
            int cuantos = Math.min((int) Math.round(lista.size() * fraccion), topePorCondominio);
            for (int i = 0; i < cuantos && i < lista.size(); i++) {
                Usuario u = lista.get(i);
                nuevos.add(Vehiculo.builder()
                        .placa(generarPlaca(placasUsadas, rnd))
                        .marca(MARCAS[rnd.nextInt(MARCAS.length)])
                        .modelo(MODELOS[rnd.nextInt(MODELOS.length)])
                        .color(COLORES[rnd.nextInt(COLORES.length)])
                        .estado(EstadoVehiculo.ACTIVO)
                        .usuario(u)
                        .build());
            }
        }

        vehiculoRepository.saveAll(nuevos);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("creados", nuevos.size());
        res.put("condominios", porCondominio.size());
        res.put("fraccion", fraccion);
        res.put("topePorCondominio", topePorCondominio);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/estacionamientos")
    @Transactional
    public ResponseEntity<Map<String, Object>> seedEstacionamientos(
            @RequestParam(defaultValue = "10") int plazasPorTorre) {

        // Tope de plazas por torre (el profe pidió ~10-11, no más).
        int plazas = Math.min(Math.max(plazasPorTorre, 1), 11);

        List<Estacionamiento> plazasNuevas = new ArrayList<>();
        int zonasCreadas = 0;

        for (Condominio condo : condominioRepository.findAll()) {
            List<Torre> torres = torreRepository.findByCondominioId(condo.getId());
            if (torres.isEmpty()) continue;

            // Aditivo: solo crea parking para las torres que AÚN no tienen su zona.
            Set<String> zonasExistentes = zonaEstacionamientoRepository.findByCondominioId(condo.getId())
                    .stream().map(ZonaEstacionamiento::getNombre).collect(Collectors.toSet());

            for (Torre torre : torres) {
                String zonaNombre = "Estac. " + torre.getNombre();
                if (zonasExistentes.contains(zonaNombre)) continue;

                ZonaEstacionamiento zona = zonaEstacionamientoRepository.save(ZonaEstacionamiento.builder()
                        .nombre(zonaNombre)
                        .descripcion("Estacionamiento de " + torre.getNombre())
                        .estado(EstadoZonaEstacionamiento.ACTIVO)
                        .condominio(condo)
                        .build());
                zonasCreadas++;

                for (int i = 1; i <= plazas; i++) {
                    plazasNuevas.add(Estacionamiento.builder()
                            .codigo(torre.getNombre() + "-" + String.format("%02d", i))
                            .estadoOcupacion(EstadoOcupacion.LIBRE)
                            .zona(zona)
                            .build());
                }
            }
        }

        estacionamientoRepository.saveAll(plazasNuevas);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("zonasCreadas", zonasCreadas);
        res.put("plazasCreadas", plazasNuevas.size());
        return ResponseEntity.ok(res);
    }

    private Long condominioId(Usuario u) {
        if (u.getCondominio() != null) {
            return u.getCondominio().getId();
        }
        if (u.getApartamento() != null
                && u.getApartamento().getPiso() != null
                && u.getApartamento().getPiso().getTorre() != null
                && u.getApartamento().getPiso().getTorre().getCondominio() != null) {
            return u.getApartamento().getPiso().getTorre().getCondominio().getId();
        }
        return null;
    }

    private String generarPlaca(Set<String> usadas, Random rnd) {
        String placa;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) sb.append(LETRAS.charAt(rnd.nextInt(LETRAS.length())));
            sb.append('-');
            sb.append(String.format("%03d", rnd.nextInt(1000)));
            placa = sb.toString();
        } while (!usadas.add(placa));
        return placa;
    }
}
