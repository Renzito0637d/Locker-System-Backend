package com.lockersystem_backend.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lockersystem_backend.Model.RegisterRequest;
import com.lockersystem_backend.Service.Interfaces.UserService;

import jakarta.transaction.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final UserService userDAO;

    public DataInitializer(UserService userService, UserService userDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
    }

    // Define los datos del admin
    private static final String ADMIN_EMAIL = "admin@gmail.com";

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Iniciando DataInitializer...");

        if (userDAO.buscarUserPorCorreo(ADMIN_EMAIL).isPresent()) {
            System.out.println("El usuario administrador ya existe. No se tomarán acciones.");
        } else {
            System.out.println("El usuario administrador no existe. Creando usuario admin por defecto...");

            try {
                RegisterRequest adminRequest = new RegisterRequest();
                adminRequest.setUserName("AdminPrin");
                adminRequest.setNombre("Admin");
                adminRequest.setApellido("Principal");
                adminRequest.setEmail(ADMIN_EMAIL);
                adminRequest.setPassword("admin123");

                userService.registerAdmin(adminRequest);

                System.out.println("Usuario administrador creado exitosamente con email: " + ADMIN_EMAIL);
                System.out.println("La contraseña por defecto es 'admin123'.");

            } catch (Exception e) {
                System.err.println("Error al intentar crear el usuario admin por defecto, porque ya existe: " + e.getMessage());
            }
        }
    }
}