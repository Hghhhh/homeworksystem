package com.hgh.homeworksystem.dao;

import com.hgh.homeworksystem.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkDao extends JpaRepository<Homework,Integer> {


    Homework findByRequestId(Integer requestId);

}
