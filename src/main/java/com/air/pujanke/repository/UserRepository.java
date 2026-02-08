package com.air.pujanke.repository;

import com.air.pujanke.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    List<Object> findAllByHasAdminPrivilegesFalse();
}
