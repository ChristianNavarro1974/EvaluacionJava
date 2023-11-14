package com.example.evaluacionjava.service;

import com.example.evaluacionjava.domain.UserDataResponse;
import com.example.evaluacionjava.domain.UserRequest;
import com.example.evaluacionjava.domain.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveUserData(UserRequest userRequest);

    UserDataResponse getUserById(Integer id);

    UserResponse updateUser(Integer id, UserRequest userDetails);

    UserResponse partialUpdateUser(Integer id, UserRequest userDetails);

    void deleteUser(Integer id);

    List<UserResponse> getAllUser();
}
