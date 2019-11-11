package com.hgh.homeworksystem.dto;

import lombok.Data;

@Data
public class HomeworkRequestForStudentDto {

    private Integer requestId;

    private String title;

    private Integer grade;

    private Integer state;

    private Integer deadline;

}
