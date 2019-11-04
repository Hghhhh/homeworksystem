package com.hgh.homeworksystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tb_homework")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "requestId")
    private HomeworkRequest homeworkRequest;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private User student;

    @Column(columnDefinition = "text")
    private String content;

    private Integer grade;

    private String enclosure;

    /**
     * 0 待提交，1 待批改 ，2 已批改
     */
    @Column(columnDefinition = "tinyint")
    private Integer state;

}


