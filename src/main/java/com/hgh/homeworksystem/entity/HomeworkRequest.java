package com.hgh.homeworksystem.entity;

import com.hgh.homeworksystem.dto.HomeworkRequestSaveDto;
import com.hgh.homeworksystem.util.TimeUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "tb_homework_request")
@DynamicInsert
@DynamicUpdate
@Data
public class HomeworkRequest {

    public HomeworkRequest(){}

    public HomeworkRequest(HomeworkRequestSaveDto homeworkRequestSaveDto){
        this.setState(0);
        this.setId(homeworkRequestSaveDto.getId());
        this.setCreateTime(TimeUtil.getUnixTime());
        this.setUpdateTime(TimeUtil.getUnixTime());
        this.setContent(homeworkRequestSaveDto.getContent());
        this.setTitle(homeworkRequestSaveDto.getTitle());
        this.setDeadline(homeworkRequestSaveDto.getDeadline());
        this.setFormat(homeworkRequestSaveDto.getFormat());
        this.setEnclosure(homeworkRequestSaveDto.getEnclosure());
        this.setTeacherId(homeworkRequestSaveDto.getTeacherId());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private Integer classId;

    private String format;

    private String enclosure;

    private String teacherId;

    private Integer deadline;

    private Integer updateTime;

    private Integer createTime;

    @Column(columnDefinition = "tinyint")
    private Integer state;

    private String simHomework;

}