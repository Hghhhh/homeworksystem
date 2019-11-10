package com.hgh.homeworksystem.dto;

import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import lombok.Data;

@Data
public class HomeworkForStudentDto {

    private HomeworkRequestDto homeworkRequest;

    private Homework homework;
}
