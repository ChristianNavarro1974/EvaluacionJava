package com.example.evaluacionjava.service.impl;

import com.example.evaluacionjava.domain.Phone;
import com.example.evaluacionjava.domain.UserRequest;
import com.example.evaluacionjava.domain.UserResponse;
import com.example.evaluacionjava.entity.UserDatum;
import com.example.evaluacionjava.entity.UserPhone;
import com.example.evaluacionjava.repository.UserDatumRepository;
import com.example.evaluacionjava.repository.UserPhoneRepository;
import com.example.evaluacionjava.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserDatumRepository userDatumRepository;
    private final UserPhoneRepository userPhoneRepository;

    public UserServiceImpl(UserDatumRepository userDatumRepository,
                           UserPhoneRepository userPhoneRepository) {
        this.userDatumRepository = userDatumRepository;
        this.userPhoneRepository = userPhoneRepository;
    }

    @Override
    @Transactional
    public UserResponse saveUserData(UserRequest userRequest) {
        UserDatum userDatum = new UserDatum();
        List<UserPhone> phones = new ArrayList<>();
        UserResponse userResponse = new UserResponse();

        userDatum.setName(userRequest.getName());
        if (checkEmailFormat(userRequest.getEmail())){
            userDatum.setEmail(userRequest.getEmail());
        } else {
            throw new RuntimeException("Email no cumple con el formato");
        }
        if (checkPasswordFormat(userRequest.getPassword())){
            userDatum.setPassword(userRequest.getPassword());
        } else {
            throw new RuntimeException("Password no cumple con el formato");
        }
        userDatum.setLastLogin(LocalDateTime.now());
        userDatum.setCreated(LocalDateTime.now());
        userDatum.setToken(generateUUID());
        userDatum.setIsactive(true);

        UserDatum saveData = userDatumRepository.save(userDatum);

        for (Phone phoneData: userRequest.getPhones()) {
            UserPhone phone = new UserPhone();
            phone.setNumber(phoneData.getNumber());
            phone.setCitycode(phoneData.getCitycode());
            phone.setContrycode(phoneData.getCountrycode());
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

    private static long get64LeastSignificantBits() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong | variant3BitFlag;
    }

    private static long get64MostSignificantBits() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        return time_low | time_mid | version | time_hi;
    }

    private static UUID generateUUID() {
        long most64SigBits = get64MostSignificantBits();
        long least64SigBits = get64LeastSignificantBits();
        return new UUID(most64SigBits, least64SigBits);
    }

    private static boolean checkEmailFormat(String email){
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private static boolean checkPasswordFormat(String password){
        Pattern pattern = Pattern.compile("^[A-Z][a-z]*[0-9]{2}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
