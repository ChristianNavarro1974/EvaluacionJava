package com.example.evaluacionjava.repository;

import com.example.evaluacionjava.entity.UserDatum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDatumRepository extends JpaRepository<UserDatum, Integer> {
}