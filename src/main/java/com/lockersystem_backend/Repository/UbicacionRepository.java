package com.lockersystem_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lockersystem_backend.Entity.Ubicacion;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
}
