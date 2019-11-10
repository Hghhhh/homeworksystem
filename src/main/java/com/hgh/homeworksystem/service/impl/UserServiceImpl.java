package com.hgh.homeworksystem.service.impl;

import com.hgh.homeworksystem.dao.ClassDao;
import com.hgh.homeworksystem.dao.UserDao;
import com.hgh.homeworksystem.dto.UserDto;
import com.hgh.homeworksystem.entity.Class;
import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.entity.User;
import com.hgh.homeworksystem.service.UserService;
import com.hgh.homeworksystem.util.PasswordUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private ClassDao classDao;

    @Override
    public UserDto login(String account, String password) {
        User user = userDao.findById(account).orElse(null);
        if(user == null){
            return null;
        }
        if(!PasswordUtil.verify(password,user.getPassword())){
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setAccount(user.getAccount());
        userDto.setState(user.getState());
        userDto.setName(user.getName());
        List<Class> classList = null;
        List<Integer> classIds = null;
        if(user.getState().equals(1)){
            classIds = classDao.findClassIdByTeacherId(user.getAccount());
        }else{
            classIds = new ArrayList<>();
            classIds.add(user.getClassId());
        }
        classList = classDao.findAllById(classIds);
        userDto.setCla(classList);
        return userDto;
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

    @Override
    public User findByAccount(String account) {
        return userDao.findById(account).orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassoword(String password, String account) {
        password = PasswordUtil.generate(password);
        userDao.updatePassoword(password,account);
    }

}
