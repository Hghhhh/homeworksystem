package com.hgh.homeworksystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tb_class")
@Data
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
