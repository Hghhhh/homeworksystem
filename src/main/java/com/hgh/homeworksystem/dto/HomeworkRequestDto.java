package com.hgh.homeworksystem.dto;

import com.hgh.homeworksystem.entity.Class;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.entity.User;
import com.hgh.homeworksystem.util.TimeUtil;
import lombok.Data;

@Data
public class HomeworkRequestDto {

    public HomeworkRequestDto(){
        super();
    }

    public HomeworkRequestDto(HomeworkRequest homeworkRequest){
        this.id = homeworkRequest.getId();
        this.title = homeworkRequest.getTitle();
        this.content = homeworkRequest.getContent();
        this.format = homeworkRequest.getFormat();
        this.enclosure = homeworkRequest.getEnclosure();
        this.deadline = TimeUtil.unixTimeToDate(homeworkRequest.getDeadline());
        this.createTime = TimeUtil.unixTimeToDate(homeworkRequest.getCreateTime());
        this.state = homeworkRequest.getState();
    }

    private Integer id;

    private String title;

    private String content;

    private Class belongClass;

    private String format;

    private String enclosure;

    private User teacher;

    private String deadline;

    private String createTime;

    private Integer state;


}
