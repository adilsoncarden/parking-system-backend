package com.condosaas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.condosaas.api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
