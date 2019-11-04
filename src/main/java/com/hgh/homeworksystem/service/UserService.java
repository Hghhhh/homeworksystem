package com.hgh.homeworksystem.service;

import com.hgh.homeworksystem.entity.User;

import java.util.List;

public interface UserService {

    User login(String account,String password);

    void save(User user);

    void insertTeacher(User user,List<Integer> classIds);


}
