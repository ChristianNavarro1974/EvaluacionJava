package com.example.evaluacionjava.repository;

import com.example.evaluacionjava.entity.UserDatum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDatumRepository extends CrudRepository<UserDatum, Integer> {

}
