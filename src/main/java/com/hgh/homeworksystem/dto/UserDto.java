package com.hgh.homeworksystem.dto;

import com.hgh.homeworksystem.entity.Class;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String account;

    private Integer state;

    private String name;

    private List<Class> cla;
}
