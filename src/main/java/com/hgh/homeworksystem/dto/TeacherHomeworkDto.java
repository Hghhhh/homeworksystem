package com.hgh.homeworksystem.dto;

import com.hgh.homeworksystem.entity.Homework;
import lombok.Data;

import java.util.List;

@Data
public class TeacherHomeworkDto {

    private List<HomeworkDto> homeworkList;

    private List<HomeworkDto> simHomeworkList;
}
