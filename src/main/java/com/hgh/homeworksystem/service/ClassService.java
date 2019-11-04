package com.hgh.homeworksystem.service;

import com.hgh.homeworksystem.entity.Class;

import java.util.List;

public interface ClassService {

    List<Class> findAll();

    Class save(Class c);
}
