package com.hgh.homeworksystem.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "tb_homework_request")
@DynamicInsert
@DynamicUpdate
@Data
public class HomeworkRequest {

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

}