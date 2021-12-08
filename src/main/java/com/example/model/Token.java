package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Token {
    @Id
    private UUID id = UUID.randomUUID();
    private String username;

    public Token() {
    }

    public Token(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
