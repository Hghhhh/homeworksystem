package com.hgh.homeworksystem.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_user")
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    private String account;

    private String password;

    @Column(columnDefinition = "tinyint")
    private Integer state;

    private Integer classId;

    private String name;
}
