package com.example.evaluacionjava.service;

import com.example.evaluacionjava.domain.UserRequest;
import com.example.evaluacionjava.domain.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveUserData(UserRequest userRequest);

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserRequest userDetails);

    UserResponse partialUpdateUser(Long id, UserRequest userDetails);

    void deleteUser(Long id);

    List<UserResponse> getAllUser();
}
