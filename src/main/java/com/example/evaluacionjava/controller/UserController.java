package com.example.evaluacionjava.controller;

import com.example.evaluacionjava.domain.Message;
import com.example.evaluacionjava.domain.UserDataResponse;
import com.example.evaluacionjava.domain.UserRequest;
import com.example.evaluacionjava.domain.UserResponse;
import com.example.evaluacionjava.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse;
        try {
            userResponse = this.userService.saveUserData(userRequest);
        } catch (DataIntegrityViolationException ex) {
            Message message = new Message();
            message.setMessage("El correo ya está registrado");
            return new ResponseEntity<>(message, HttpStatus.ALREADY_REPORTED);
        } catch (Exception ex) {
            Message message = new Message();
            message.setMessage(ex.getMessage());
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUser() {
        try{
            List<UserResponse> users = userService.getAllUser();
            if (users != null) {
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            Message message = new Message();
            message.setMessage(ex.getMessage());
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        UserDataResponse user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody UserRequest userDetails) {
        UserResponse updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> partialUpdateUser(@PathVariable Integer id, @RequestBody UserRequest userDetails) {
        UserResponse updatedUser = userService.partialUpdateUser(id, userDetails);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
