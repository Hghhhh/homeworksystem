package com.hgh.homeworksystem.service.impl;

import com.hgh.homeworksystem.dao.AdminDao;
import com.hgh.homeworksystem.entity.Admin;
import com.hgh.homeworksystem.service.AdminService;
import com.hgh.homeworksystem.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminDao adminDao;

    @Override
    public boolean login(String account, String password) {
        Optional<Admin> admin = adminDao.findById(account);
        if(!admin.isPresent()){
            return false;
        }
        return PasswordUtil.verify(password,admin.get().getPassword());
    }
}
