package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
