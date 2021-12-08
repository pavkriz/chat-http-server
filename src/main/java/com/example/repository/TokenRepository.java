package com.example.repository;

import com.example.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    void deleteByUsername(String username);
}
