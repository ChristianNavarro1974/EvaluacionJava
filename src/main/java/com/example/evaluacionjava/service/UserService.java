package com.example.evaluacionjava.service;

import com.example.evaluacionjava.domain.UserRequest;
import com.example.evaluacionjava.domain.UserResponse;

public interface UserService {
    UserResponse saveUserData(UserRequest userRequest);
}
