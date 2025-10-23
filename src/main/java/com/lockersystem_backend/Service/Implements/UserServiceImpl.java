package com.lockersystem_backend.Service.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lockersystem_backend.Config.JwtService;
import com.lockersystem_backend.Entity.User;
import com.lockersystem_backend.Entity.Enum.RoleName;
import com.lockersystem_backend.Model.AuthResponse;
import com.lockersystem_backend.Model.RegisterRequest;
import com.lockersystem_backend.Repository.UserRepository;
import com.lockersystem_backend.Service.Interfaces.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // ---------------- VALIDACIÓN DE REGISTRO ----------------
    @Override
    public AuthResponse registerEstudiante(RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }

        var encodedPassword = passwordEncoder.encode(request.getPassword());
        var user = User.from(request, encodedPassword, RoleName.ESTUDIANTE);
        userRepository.save(user);

        var jwtToken = jwtService.generateAccessToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    // ---------------- CRUD CON VALIDACIONES ----------------

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EntityNotFoundException("No hay usuarios registrados");
        }
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID no es válido");
        }
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
        }
        return user;
    }

    @Override
    public User save(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID no es válido para eliminar");
        }
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. El usuario no existe con ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
