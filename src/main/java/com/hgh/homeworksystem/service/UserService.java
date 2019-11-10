package com.hgh.homeworksystem.service;

import com.hgh.homeworksystem.dto.UserDto;
import com.hgh.homeworksystem.entity.User;

import java.util.List;

public interface UserService {

    UserDto login(String account, String password);

    void save(User user);

    void insertTeacher(User user,List<Integer> classIds);

    User findByAccount(String account);

    void updatePassoword(String password, String account);

}
