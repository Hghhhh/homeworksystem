package com.hgh.homeworksystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class HomeworkRequestSaveDto {

    private Integer id;

    private String title;

    private String content;

    private List<Integer> classIds;

    private String format;

    private String enclosure;

    private String teacherId;

    private Integer deadline;

}
