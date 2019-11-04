package com.hgh.homeworksystem.dto;

public class CodeMsg {

    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg FLASHSALE_ERROR = new CodeMsg(1,"调用太过频繁");
    public static CodeMsg SERVER_ERROR = new CodeMsg(5, "服务端异常");
    public static CodeMsg UNFINDUSER_ERROR = new CodeMsg(2,"该用户还没有授权");
    public static CodeMsg PARAM_ERROR = new CodeMsg(3,"参数错误");
    public static CodeMsg ROOMFULL_ERROR = new CodeMsg(4,"房间已满");
    public static CodeMsg METHOD_ERROR = new CodeMsg(6,"方法不支持");
    public static CodeMsg NOT_EXIT_PICTURE = new CodeMsg(7,"该分类还没有图片");


    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}