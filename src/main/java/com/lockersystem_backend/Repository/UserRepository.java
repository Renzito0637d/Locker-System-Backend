package com.lockersystem_backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lockersystem_backend.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  // Devuelve un Optional<User> para manejar el caso en el que no se encuentre un
  // usuario con ese usuario.
  boolean existsByEmail(String email);

  boolean existsByUserName(String userName);

  Optional<User> findUserByUserName(String userName);

}
