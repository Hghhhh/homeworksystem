package com.hgh.homeworksystem.controller;

import com.hgh.homeworksystem.dto.Result;
import com.hgh.homeworksystem.dto.UserDto;
import com.hgh.homeworksystem.entity.User;
import com.hgh.homeworksystem.service.UserService;
import com.hgh.homeworksystem.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("login")
    public Result<UserDto> login(@RequestBody String user){
        User user1 = JsonUtil.parseJsonWithGson(user,User.class);
        UserDto user2 = userService.login(user1.getAccount(), user1.getPassword());
        return Result.success(user2);
    }

    @PostMapping("updateAccount")
    public Result<Void> updateAccount(@RequestBody String user){
        User user1 = JsonUtil.parseJsonWithGson(user,User.class);
        userService.updatePassoword(user1.getPassword(), user1.getAccount());
        return Result.success(null);
    }

}
