package com.hgh.homeworksystem.constant;

public enum HomeStateConstant {

    TO_BE_SUBMITTED(0,"待提交"),
    TO_BE_CORRECTED(1,"待批改"),
    BE_CORRECTED(2,"已批改"),
    NOT_FINISHED(3,"未完成");

    private Integer state;

    private String des;

    HomeStateConstant(Integer state, String des){
        this.state = state;
        this.des = des;
    }

}
