package com.condosaas.api.module.auth.controller;

import com.condosaas.api.config.JwtUtils;
import com.condosaas.api.module.auth.dto.LoginRequestDTO;
import com.condosaas.api.module.auth.dto.LoginResponseDTO;
import com.condosaas.api.module.auth.dto.UsuarioAuthDTO;
import com.condosaas.api.module.permiso.service.PermissionAuthorizationService;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final PermissionAuthorizationService permissionAuthorizationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(403).body("Contraseña incorrecta");
        }

        String rolNombre = usuario.getRol().getNombre();
        List<String> permisos = permissionAuthorizationService.resolvePermisosForRol(
                usuario.getRol().getId(), rolNombre);

        Long condominioId = usuario.getCondominio() != null ? usuario.getCondominio().getId() : null;
        String condominioNombre = usuario.getCondominio() != null ? usuario.getCondominio().getNombre() : null;

        String token = jwtUtils.generateToken(usuario.getEmail(), rolNombre, condominioId, permisos);

        UsuarioAuthDTO usuarioDto = UsuarioAuthDTO.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .rolId(usuario.getRol().getId())
                .rolNombre(rolNombre)
                .condominioId(condominioId)
                .condominioNombre(condominioNombre)
                .build();

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token(token)
                .tipo("Bearer")
                .usuario(usuarioDto)
                .permisos(permisos)
                .build();

        return ResponseEntity.ok(response);
    }
}
