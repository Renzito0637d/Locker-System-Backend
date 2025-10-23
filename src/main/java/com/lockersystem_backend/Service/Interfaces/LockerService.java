package com.lockersystem_backend.Service.Interfaces;

import java.util.List;
import java.util.Optional;
import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Model.CreateLockerRequest;
import com.lockersystem_backend.Model.UpdateLockerRequest;

public interface LockerService {
    List<Locker> findAll();
    Optional<Locker> findById(Long id);
    Locker create(CreateLockerRequest dto);
    Optional<Locker> update(Long id, UpdateLockerRequest dto);
    void deleteById(Long id);
}
