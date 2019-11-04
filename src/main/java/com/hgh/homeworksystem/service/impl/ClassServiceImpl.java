package com.hgh.homeworksystem.service.impl;

import com.hgh.homeworksystem.dao.ClassDao;
import com.hgh.homeworksystem.entity.Class;
import com.hgh.homeworksystem.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService{
    @Autowired
    private ClassDao classDao;

    @Override
    public List<Class> findAll() {
        return classDao.findAll();
    }

    @Override
    public Class save(Class c) {
        return classDao.save(c);
    }
}
