package com.lockersystem_backend.Repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lockersystem_backend.Entity.Ubicacion;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    boolean existsByNombreEdificioAndPisoAndPabellon(String nombreEdificio, String piso, String pabellon);
    Optional<Ubicacion> findByNombreEdificioAndPisoAndPabellon(String nombreEdificio, String piso, String pabellon);
}

