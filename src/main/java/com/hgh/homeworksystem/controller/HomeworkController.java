package com.hgh.homeworksystem.controller;

import com.hgh.homeworksystem.dto.*;
import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.service.HomeworkRequestService;
import com.hgh.homeworksystem.service.HomeworkService;
import com.hgh.homeworksystem.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private HomeworkRequestService homeworkRequestService;

    @PostMapping("homework")
    public Result<Void> postHomework(@RequestBody String homeworkStr){
        Homework homework = JsonUtil.parseJsonWithGson(homeworkStr,Homework.class);
        homeworkService.save(homework);
        return Result.success(null);
    }

    @PutMapping("homework")
    public Result<Void> putHomework(@RequestBody String homeworkStr){
        Homework homework = JsonUtil.parseJsonWithGson(homeworkStr,Homework.class);
        homeworkService.updateGrade(homework.getId(),homework.getGrade(),homework.getRemarks());
        return Result.success(null);
    }

    @GetMapping("homework/{requestId}")
    public Result<TeacherHomeworkDto> getHomeworkByRequestId(@PathVariable Integer requestId){
        return Result.success(homeworkService.getDtoByRequestId(requestId));
    }

    @GetMapping("homework/{requestId}/{studentId}")
    public Result<HomeworkForStudentDto> getHomeworkByRequestId(@PathVariable(name = "requestId") Integer requestId, @PathVariable(name="studentId")String studentId){
        return Result.success(homeworkService.getByRequestIdAndStudent(requestId,studentId));
    }

    @GetMapping("homeworkDetail/{homeworkId}")
    public Result<Homework> getHomeworkDetail(@PathVariable Integer homeworkId){
        return Result.success(homeworkService.getById(homeworkId));
    }


}
