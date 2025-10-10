package com.lockersystem_backend.Service.Interfaces;

import com.lockersystem_backend.Model.AuthResponse;
import com.lockersystem_backend.Model.RegisterRequest;

public interface UserService {
    AuthResponse registerCliente(RegisterRequest request);
}
