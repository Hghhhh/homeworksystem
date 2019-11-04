package com.hgh.homeworksystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_admin")
@Data
public class Admin {

    @Id
    private String account;

    private String password;
}
