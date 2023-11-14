package com.example.evaluacionjava.repository;

import com.example.evaluacionjava.entity.UserPhone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPhoneRepository extends CrudRepository<UserPhone, Integer> {


}
