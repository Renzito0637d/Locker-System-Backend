package com.lockersystem_backend.Service.Interfaces;

import com.lockersystem_backend.Model.AuthResponse;
import com.lockersystem_backend.Model.RegisterRequest;
import com.lockersystem_backend.Entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    AuthResponse registerEstudiante(RegisterRequest request);

    // Métodos CRUD simples
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
