package com.lockersystem_backend.Service.Implements;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.lockersystem_backend.Config.JwtService;
import com.lockersystem_backend.Entity.User;
import com.lockersystem_backend.Entity.Enum.RoleName;
import com.lockersystem_backend.Model.AuthResponse;
import com.lockersystem_backend.Model.RegisterRequest;
import com.lockersystem_backend.Repository.UserRepository;
import com.lockersystem_backend.Service.Interfaces.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse registerCliente(RegisterRequest request) {
        var encodedPassword = passwordEncoder.encode(request.getPassword());

        var user = User.from(request, encodedPassword, RoleName.CLIENTE);
        // Guardar el nuevo usuario en la base de datos
        userRepository.save(user);
        // Generar un token JWT para el usuario registrado
        var jwtToken = jwtService.generateAccessToken(user);
        // Devolver el token generado dentro de un objeto AuthResponse
        return AuthResponse.builder().token(jwtToken).build();
    }

}
