package com.hgh.homeworksystem.dao;

import com.hgh.homeworksystem.entity.HomeworkRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRequestDao extends JpaRepository<HomeworkRequest, Integer>{


    List<HomeworkRequest> findByTeacherIdAndCreateTimeBetweenOrderByCreateTime(String teacher,Integer begin,Integer end);

    List<HomeworkRequest> findByTeacherIdAndCreateTimeGreaterThanOrderByCreateTimeDesc(String teacher, Integer time);

    List<HomeworkRequest> findByClassIdAndCreateTimeBetweenOrderByCreateTimeDesc(Integer classId, Integer begin,Integer end);

    List<HomeworkRequest> findByClassIdAndCreateTimeGreaterThanOrderByCreateTimeDesc(Integer classId, Integer time);

    List<HomeworkRequest> findByStateAndDeadlineLessThan(Integer state, Integer time);

}
