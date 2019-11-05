package com.hgh.homeworksystem.service;

import com.hgh.homeworksystem.entity.Homework;

public interface HomeworkService {

    void save(Homework homework);


    Homework getByRequestId(Integer requestId);

}
