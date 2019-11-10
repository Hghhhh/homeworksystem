package com.hgh.homeworksystem.service;

import com.hgh.homeworksystem.dto.HomeworkDto;
import com.hgh.homeworksystem.dto.HomeworkForStudentDto;
import com.hgh.homeworksystem.dto.TeacherHomeworkDto;
import com.hgh.homeworksystem.entity.Homework;

import java.util.List;

public interface HomeworkService {

    void save(Homework homework);

    Homework getById(Integer id);

    TeacherHomeworkDto getDtoByRequestId(Integer requestId);

    List<Homework> getByRequestId(Integer requestId);

    HomeworkForStudentDto getByRequestIdAndStudent(Integer requestId, String student);

    void updateGrade(Integer id, Integer grade, String remarks);
}
