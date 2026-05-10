package com.condosaas.api.torres.repository;

import com.condosaas.api.torres.entity.Torres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorresRepository extends JpaRepository<Torres,Long> {
}
