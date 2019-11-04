package com.hgh.homeworksystem.service.impl;

import com.hgh.homeworksystem.dao.UserDao;
import com.hgh.homeworksystem.entity.User;
import com.hgh.homeworksystem.service.UserService;
import com.hgh.homeworksystem.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public User login(String account, String password) {
        User user = userDao.findById(account).orElse(null);

        if(user == null){
            return null;
        }
        if(!PasswordUtil.verify(password,user.getPassword())){
            return null;
        }
        return user;
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void insertTeacher(User user, List<Integer> classIds) {
        userDao.save(user);
        for(Integer classId : classIds){
            userDao.insertTeacherClass(user.getAccount(),classId);
        }
    }

}
