package com.hgh.homeworksystem.dao;

import com.hgh.homeworksystem.entity.HomeworkRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRequestDao extends JpaRepository<HomeworkRequest, Integer>{


    List<HomeworkRequest> findByTeacherIdAndCreateTimeBetween(String teacher,Integer begin,Integer end);
}
