package com.lockersystem_backend;

import com.lockersystem_backend.Service.Implements.LockerServiceImpl;
import com.lockersystem_backend.Service.Implements.UbicacionServiceImpl;
import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Entity.Ubicacion;
import com.lockersystem_backend.Model.LockerDTOs.CreateLockerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LockerServiceTest {

    @Autowired
    private LockerServiceImpl lockerService;

    @Autowired
    private UbicacionServiceImpl ubicacionService;

    @Test
    void debeCrearLockerCorrectamente() {

        // 1️ Crear ubicación de prueba usando el método REAL del servicio
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setNombreEdificio("Edificio B");
        ubicacion.setPabellon("B");
        ubicacion.setPiso("16");
        ubicacion.setDescripcion("Ubicaciónpruebita");

        Ubicacion ubicacionGuardada = ubicacionService.registrarUbicacion(ubicacion);

        // 2️ Crear DTO del locker
        CreateLockerRequest dto = new CreateLockerRequest();
        dto.setNumeroLocker("A1");
        dto.setEstado("DISPONIBLE");
        dto.setUbicacionId(ubicacionGuardada.getId());

        // 3️ Llamar al método del servicio
        Locker l = lockerService.create(dto);

        // 4️ Validaciones
        assertNotNull(l, "El locker no debe ser nulo");
        assertEquals("A1", l.getNumeroLocker(), "Debe coincidir el número del locker");
        assertEquals("DISPONIBLE", l.getEstado(), "Debe coincidir el estado");
        assertNotNull(l.getUbicacion(), "La ubicación no debe ser nula");
        assertEquals(ubicacionGuardada.getId(), l.getUbicacion().getId(), "Debe asignarse la ubicación correcta");
    }

    @Test
void noDebeCrearLockerDuplicado() {

    // 1️ Crear ubicación
    Ubicacion u = new Ubicacion();
    u.setNombreEdificio("Axcsss");
    u.setPabellon("B");
    u.setPiso("12");
    u.setDescripcion("test");
    Ubicacion ubicacion = ubicacionService.registrarUbicacion(u);

    // 2️ Crear locker inicial
    CreateLockerRequest dto = new CreateLockerRequest();
    dto.setNumeroLocker("z1");
    dto.setEstado("DISPONIBLE");
    dto.setUbicacionId(ubicacion.getId());
    lockerService.create(dto);

    //  Intentar crear el mismo locker otra vez
    CreateLockerRequest dtoDuplicado = new CreateLockerRequest();
    dtoDuplicado.setNumeroLocker("z1");
    dtoDuplicado.setEstado("DISPONIBLE");
    dtoDuplicado.setUbicacionId(ubicacion.getId());

    // 4️ Validar que lance conflicto (409)
    assertThrows(ResponseStatusException.class, () -> {
        lockerService.create(dtoDuplicado);
    });
}
@Test
void noDebeCrearLockerConUbicacionInexistente() {

    CreateLockerRequest dto = new CreateLockerRequest();
    dto.setNumeroLocker("Z9");
    dto.setEstado("DISPONIBLE");
    dto.setUbicacionId(9999L); // ID que no existe

    try {
        lockerService.create(dto);
        fail("Debió lanzar un error por ubicación inexistente");

    } catch (ResponseStatusException ex) {

        // 
        System.out.println(" ERROR CAPTURADO:");
        System.out.println("Código HTTP → " + ex.getStatusCode());
        System.out.println("Mensaje     → " + ex.getReason());

        // Validación para JUnit
        assertEquals("Ubicación no encontrada", ex.getReason());
    }
}
}
