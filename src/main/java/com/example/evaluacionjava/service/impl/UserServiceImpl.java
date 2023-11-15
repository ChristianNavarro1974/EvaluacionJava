package com.example.evaluacionjava.service.impl;

import com.example.evaluacionjava.domain.Phone;
import com.example.evaluacionjava.domain.UserDataResponse;
import com.example.evaluacionjava.domain.UserRequest;
import com.example.evaluacionjava.domain.UserResponse;
import com.example.evaluacionjava.entity.UserDatum;
import com.example.evaluacionjava.entity.UserPhone;
import com.example.evaluacionjava.repository.UserDatumRepository;
import com.example.evaluacionjava.repository.UserPhoneRepository;
import com.example.evaluacionjava.service.UserService;
import com.example.evaluacionjava.util.DataTool;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final DataTool dataTool;
    private final UserDatumRepository userDatumRepository;
    private final UserPhoneRepository userPhoneRepository;

    public UserServiceImpl(UserDatumRepository userDatumRepository,
                           UserPhoneRepository userPhoneRepository, DataTool dataTool) {
        this.userDatumRepository = userDatumRepository;
        this.userPhoneRepository = userPhoneRepository;
        this.dataTool = dataTool;
    }

    @Override
    @Transactional
    public UserResponse saveUserData(UserRequest userRequest) {
        UserDatum userDatum = new UserDatum();
        UserResponse userResponse = new UserResponse();

        userDatum.setName(userRequest.getName());
        if (this.dataTool.checkEmailFormat(userRequest.getEmail())) {
            userDatum.setEmail(userRequest.getEmail());
        } else {
            throw new RuntimeException("Email no cumple con el formato");
        }
        if (this.dataTool.checkPasswordFormat(userRequest.getPassword())) {
            userDatum.setPassword(userRequest.getPassword());
        } else {
            throw new RuntimeException("Password no cumple con el formato");
        }
        userDatum.setCreated(LocalDateTime.now());
        userDatum.setToken(this.dataTool.generateUUID());
        userDatum.setLastLogin(LocalDateTime.now());
        userDatum.setIsactive(true);
        UserDatum saveData = userDatumRepository.save(userDatum);
        for (Phone phoneData : userRequest.getPhones()) {
            UserPhone phone = new UserPhone();
            phone.setNumber(phoneData.getNumber());
            phone.setCitycode(phoneData.getCitycode());
            phone.setCountrycode(phoneData.getCountrycode());
            phone.setUser(saveData);
            userPhoneRepository.save(phone);
        }

        userResponse.setLastLogin(saveData.getLastLogin());
        userResponse.setUserId(saveData.getId().toString());
        userResponse.setActive(saveData.getIsactive());
        userResponse.setCreatedDate(saveData.getCreated());
        userResponse.setToken(saveData.getToken());

        return userResponse;
    }

    @Override
    public UserDataResponse getUserById(Integer id) {
        UserDataResponse userResponse = null;

        UserDatum userDatum = userDatumRepository.findById(id).orElse(null);
        if (userDatum != null) {
            userResponse = new UserDataResponse();
            userResponse.setName(userDatum.getName());
            userResponse.setActive(userDatum.getIsactive());
            userResponse.setCreatedDate(userDatum.getCreated());
            userResponse.setLastLogin(userDatum.getLastLogin());
            userResponse.setEmail(userDatum.getEmail());
            List<UserPhone> userPhones = userDatum.getUserPhones();
            List<Phone> phoneList = new ArrayList<>();
            for (UserPhone phone : userPhones) {
                Phone phoneData = new Phone();
                phoneData.setNumber(phone.getNumber());
                phoneData.setCitycode(phone.getCitycode());
                phoneData.setCountrycode(phone.getCountrycode());
                phoneList.add(phoneData);
            }
            userResponse.setPhones(phoneList);
        }

        return userResponse;
    }

    @Override
    @Transactional
    public UserResponse updateUser(Integer id, UserRequest userRequest) {

        UserDatum userDatum = userDatumRepository.findById(id).orElse(null);

        if (userDatum == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        UserResponse userResponse = new UserResponse();

        userDatum.setName(userRequest.getName());
        if (this.dataTool.checkEmailFormat(userRequest.getEmail())) {
            userDatum.setEmail(userRequest.getEmail());
        } else {
            throw new RuntimeException("Email no cumple con el formato");
        }
        if (this.dataTool.checkPasswordFormat(userRequest.getPassword())) {
            userDatum.setPassword(userRequest.getPassword());
        } else {
            throw new RuntimeException("Password no cumple con el formato");
        }
        userDatum.setModified(LocalDateTime.now());
        userDatum.setIsactive(true);
        userPhoneRepository.deleteByUser(userDatum);
        userDatum.setUserPhones(new ArrayList<>());
        for (Phone phoneData : userRequest.getPhones()) {
            UserPhone phone = new UserPhone();
            phone.setNumber(phoneData.getNumber());
            phone.setCitycode(phoneData.getCitycode());
            phone.setCountrycode(phoneData.getCountrycode());
            phone.setUser(userDatum);
            userDatum.getUserPhones().add(phone);
        }
        UserDatum saveData = userDatumRepository.save(userDatum);

        userResponse.setLastLogin(saveData.getLastLogin());
        userResponse.setUserId(saveData.getId().toString());
        userResponse.setActive(saveData.getIsactive());
        userResponse.setCreatedDate(saveData.getCreated());
        userResponse.setToken(saveData.getToken());

        return userResponse;
    }

    @Override
    public UserResponse partialUpdateUser(Integer id, UserRequest userRequest) {
        UserDatum userDatum = userDatumRepository.findById(id).orElse(null);
        UserResponse userResponse = new UserResponse();

        if (userDatum == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        if (!Strings.isEmpty(userRequest.getName())) {
            userDatum.setName(userRequest.getName());
        }
        if (!Strings.isEmpty(userRequest.getEmail()) && this.dataTool.checkEmailFormat(userRequest.getEmail())) {
            userDatum.setEmail(userRequest.getEmail());
        } else if (!Strings.isEmpty(userRequest.getEmail()) && !this.dataTool.checkEmailFormat(userRequest.getEmail())) {
            throw new RuntimeException("Email no cumple con el formato");
        }

        if (!Strings.isEmpty(userRequest.getPassword()) && this.dataTool.checkPasswordFormat(userRequest.getPassword())) {
            userDatum.setPassword(userRequest.getPassword());
        } else if (!Strings.isEmpty(userRequest.getPassword()) && !this.dataTool.checkPasswordFormat(userRequest.getPassword())) {
            throw new RuntimeException("Password no cumple con el formato");
        }

        userDatum.setModified(LocalDateTime.now());
        userDatum.setIsactive(true);

        for (Phone phoneData : userRequest.getPhones()) {
            UserPhone phone = new UserPhone();
            phone.setNumber(phoneData.getNumber());
            phone.setCitycode(phoneData.getCitycode());
            phone.setCountrycode(phoneData.getCountrycode());
            phone.setUser(userDatum);
            userDatum.getUserPhones().add(phone);
        }
        UserDatum saveData = userDatumRepository.save(userDatum);

        userResponse.setLastLogin(saveData.getLastLogin());
        userResponse.setUserId(saveData.getId().toString());
        userResponse.setActive(saveData.getIsactive());
        userResponse.setCreatedDate(saveData.getCreated());
        userResponse.setToken(saveData.getToken());

        return userResponse;
    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public List<UserResponse> getAllUser() {
        List<UserResponse> users = new ArrayList<>();

        Iterable<UserDatum> userDataList = userDatumRepository.findAll();
        for (UserDatum userDatum : userDataList) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(userDatum.getId().toString());
            userResponse.setActive(userDatum.getIsactive());
            userResponse.setCreatedDate(userDatum.getCreated());
            userResponse.setLastLogin(userDatum.getLastLogin());
            userResponse.setToken(userDatum.getToken());
            users.add(userResponse);
        }

        return users;
    }
}
