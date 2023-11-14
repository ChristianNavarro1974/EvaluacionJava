package com.example.evaluacionjava.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponse {
    @JsonProperty("nombre")
    String name;
    @JsonProperty("correo")
    String email;
    @JsonProperty("telefonos")
    List<Phone> phones;
    @JsonProperty
    LocalDateTime createdDate;
    @JsonProperty
    LocalDateTime modificatedDate;
    @JsonProperty
    LocalDateTime lastLogin;
    @JsonProperty
    Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
