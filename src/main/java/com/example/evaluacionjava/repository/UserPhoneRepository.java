package com.example.evaluacionjava.repository;

import com.example.evaluacionjava.entity.UserDatum;
import com.example.evaluacionjava.entity.UserPhone;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserPhoneRepository extends CrudRepository<UserPhone, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM UserPhone p WHERE p.user = :user")
    void deleteByUser(@Param("user") UserDatum user);
}
