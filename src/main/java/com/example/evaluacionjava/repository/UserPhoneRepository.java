package com.example.evaluacionjava.repository;

import com.example.evaluacionjava.entity.UserDatum;
import com.example.evaluacionjava.entity.UserPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPhoneRepository extends JpaRepository<UserPhone, Integer> {


    @Query("DELETE FROM UserPhone p WHERE p.user = :user")
    void deleteByUser(@Param("user") UserDatum user);
}
