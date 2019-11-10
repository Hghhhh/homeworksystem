package com.hgh.homeworksystem.dto;

import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.entity.User;
import lombok.Data;

@Data
public class HomeworkDto {

    private Integer id;

    private Integer requestId;

    private User student;

    private String content;

    private Integer grade;

    private String enclosure;

    /**
     * 0 待提交，1 待批改 ，2 已批改
     */
    private Integer state;

    private Integer createTime;

    private Integer updateTime;

    public HomeworkDto(){}

    public HomeworkDto(Homework homework){
        this.setId(homework.getId());
        this.setContent(homework.getContent());
        this.setCreateTime(homework.getCreateTime());
        this.setEnclosure(homework.getEnclosure());
        this.setGrade(homework.getGrade());
        this.setState(homework.getState());
        this.setUpdateTime(homework.getUpdateTime());
        this.setRequestId(homework.getRequestId());
    }

}
