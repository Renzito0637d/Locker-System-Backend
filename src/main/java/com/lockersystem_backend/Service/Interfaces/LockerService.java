package com.lockersystem_backend.Service.Interfaces;

import java.util.List;
import java.util.Optional;
import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Model.LockerDTOs.CreateLockerRequest;
import com.lockersystem_backend.Model.LockerDTOs.LockerResponse;
import com.lockersystem_backend.Model.LockerDTOs.UpdateLockerRequest;

public interface LockerService {
    List<LockerResponse> findAll();
    Optional<Locker> findById(Long id);
    Locker create(CreateLockerRequest dto);
    Optional<Locker> update(Long id, UpdateLockerRequest dto);
    void deleteById(Long id);
    Optional<Locker> findByNumeroLocker(String numeroLocker);
}
