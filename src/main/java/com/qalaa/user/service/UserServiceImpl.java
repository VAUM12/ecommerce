package com.qalaa.user.service;

import com.qalaa.exception.ResourceNotFoundException;
import com.qalaa.user.mapper.UserMapper;
import com.qalaa.user.model.User;
import com.qalaa.user.reposotory.UserRepository;
import com.qalaa.user.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserWrapper userWrapper) {
        User user = UserMapper.mapToUserWrapper(userWrapper);
//        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(userWrapper.getPassword()));
        user.setPassword(userWrapper.getPassword());
        user.setVerified(false);
        user.setOtp(generateOtp());
        userRepository.save(user);
        return user;
    }

    @Override
    public UserWrapper verifyOtp(String email, String otp) {
        Optional<User> existUser = userRepository.findByEmail(email);
        if (!existUser.isPresent()) {
            throw new ResourceNotFoundException("User not found.");
        }
        if(existUser.get().getOtp().equals(otp)){
            existUser.get().setVerified(true);
            existUser.get().setOtp(null);
            userRepository.save(existUser.get());
            return UserMapper.mapToUserWrapper(existUser.get());
        }
        throw new ResourceNotFoundException("Invalid otp.");
    }

    private String generateOtp() {
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }
}
