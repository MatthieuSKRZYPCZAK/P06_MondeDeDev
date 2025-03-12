package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
