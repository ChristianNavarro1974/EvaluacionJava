package com.example.evaluacionjava.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty
    String userId;
    @JsonProperty
    LocalDateTime createdDate;
    @JsonProperty
    LocalDateTime modificatedDate;
    @JsonProperty
    LocalDateTime lastLogin;
    @JsonProperty
    UUID token;
    @JsonProperty
    Boolean active;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModificatedDate() {
        return modificatedDate;
    }

    public void setModificatedDate(LocalDateTime modificatedDate) {
        this.modificatedDate = modificatedDate;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
