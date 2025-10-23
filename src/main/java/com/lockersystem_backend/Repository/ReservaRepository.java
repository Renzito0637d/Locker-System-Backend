package com.lockersystem_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lockersystem_backend.Entity.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Métodos personalizados si los tienes
}
