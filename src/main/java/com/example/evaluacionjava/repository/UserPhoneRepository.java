package com.example.evaluacionjava.repository;

import com.example.evaluacionjava.entity.UserPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPhoneRepository extends JpaRepository<UserPhone, Integer> {
}