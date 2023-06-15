package com.example.evaluacionjava.controller;

import com.example.evaluacionjava.domain.Message;
import com.example.evaluacionjava.domain.UserRequest;
import com.example.evaluacionjava.domain.UserResponse;
import com.example.evaluacionjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> userCreate(@RequestBody UserRequest userRequest) {
        UserResponse userResponse;
        try {
            userResponse = this.userService.saveUserData(userRequest);
        } catch (DataIntegrityViolationException ex) {
            Message message = new Message();
            message.setMessage("El correo ya registrado");
            return new ResponseEntity<>(message, HttpStatus.ALREADY_REPORTED);
        } catch (Exception ex) {
            Message message = new Message();
            message.setMessage(ex.getMessage());
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
