package com.qalaa.user.mapper;

import com.qalaa.user.model.User;
import com.qalaa.user.wrapper.UserWrapper;

public class UserMapper {

    public static User mapToUserWrapper(UserWrapper userWrapper) {
        User user = new User();
        user.setName(userWrapper.getName());
        user.setEmail(userWrapper.getEmail());
        user.setMobileNumber(userWrapper.getMobileNumber());
        return user;

    }

    public static UserWrapper mapToUserWrapper(User user,String token) {
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setName(user.getName());
        userWrapper.setEmail(user.getEmail());
        userWrapper.setMobileNumber(user.getMobileNumber());
        userWrapper.setToken(token);
        return userWrapper;
    }
}
