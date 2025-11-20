package com.lockersystem_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lockersystem_backend.Entity.Locker;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
     boolean existsByNumeroLocker(String numeroLocker);
}
