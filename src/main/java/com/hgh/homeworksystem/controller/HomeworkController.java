package com.hgh.homeworksystem.controller;

import com.hgh.homeworksystem.dto.Result;
import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.service.HomeworkService;
import com.hgh.homeworksystem.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @PostMapping("homework")
    public Result<Void> postHomework(@RequestBody String homeworkStr){
        Homework homework = JsonUtil.parseJsonWithGson(homeworkStr,Homework.class);
        homeworkService.save(homework);
        return Result.success(null);
    }

    @GetMapping("homework/{requestId}")
    public Result<Homework> getHomeworkByRequestId(@PathVariable Integer requestId){
        return Result.success(homeworkService.getByRequestId(requestId));
    }

}
