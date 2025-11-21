package com.lockersystem_backend.Controller;

import com.lockersystem_backend.Entity.User;
import com.lockersystem_backend.Entity.Enum.RoleName;
import com.lockersystem_backend.Model.RegisterRequest;
import com.lockersystem_backend.Model.UserDTOs.CreateUserRequest;
import com.lockersystem_backend.Service.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        RegisterRequest registerReq = new RegisterRequest();

        // --- CORRECCIÓN CRÍTICA: TRIM (Eliminar espacios) ---
        String cleanUser = request.getUserName() != null ? request.getUserName().trim() : "";
        String cleanEmail = request.getEmail() != null ? request.getEmail().trim() : "";

        registerReq.setUserName(cleanUser);
        registerReq.setNombre(request.getNombre());
        registerReq.setApellido(request.getApellido());
        registerReq.setEmail(cleanEmail);
        registerReq.setPassword(request.getPassword());

        try {
            if ("ADMIN".equals(request.getRole())) {
                return ResponseEntity.ok(userService.registerAdmin(registerReq));
            } else {
                return ResponseEntity.ok(userService.registerEstudiante(registerReq));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody CreateUserRequest request) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();

        // --- LIMPIEZA Y VALIDACIONES ---
        String newEmail = request.getEmail() != null ? request.getEmail().trim() : "";
        String newUserName = request.getUserName() != null ? request.getUserName().trim() : "";
        String currentEmail = user.getEmail() != null ? user.getEmail().trim() : "";
        String currentUserName = user.getUserName() != null ? user.getUserName().trim() : "";

        if (!currentEmail.equalsIgnoreCase(newEmail) && userService.existsByEmail(newEmail)) {
            return ResponseEntity.badRequest().body("El correo ya está en uso.");
        }

        if (!currentUserName.equalsIgnoreCase(newUserName) && userService.existsByUserName(newUserName)) {
            return ResponseEntity.badRequest().body("El usuario ya está en uso.");
        }

        // --- ACTUALIZACIÓN DE CAMPOS ---
        user.setUserName(newUserName);
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(newEmail);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        // --- ACTUALIZACIÓN DE ROLES (SOLUCIÓN SEGURA) ---
        try {
            String roleStr = request.getRole();

            RoleName roleName = RoleName.valueOf(roleStr);

            // En lugar de crear un Set nuevo y hacer setRoles (que causa el error),
            // modificamos la colección existente de Hibernate.
            user.getRoles().clear();
            user.getRoles().add(roleName);

        } catch (Exception e) {
            System.err.println("Error actualizando rol: " + e.getMessage());
        }

        return ResponseEntity.ok(userService.save(user));
    }

    // Endpoint para "Desactivar" en lugar de borrar físicamente (Opcional, pero
    // recomendado)
    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<?> toggleActive(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty())
            return ResponseEntity.notFound().build();

        User user = userOptional.get();
        user.setActive(!user.getActive()); // Invierte el estado
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}