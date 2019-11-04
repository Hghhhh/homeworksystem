package com.hgh.homeworksystem.controller;

import com.hgh.homeworksystem.dto.HomeworkRequestDto;
import com.hgh.homeworksystem.dto.Result;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.service.HomeworkRequestService;
import com.hgh.homeworksystem.util.JsonUtil;
import com.hgh.homeworksystem.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeworkRequestController {

    @Autowired
    private HomeworkRequestService homeworkRequestService;

    @PostMapping("publishHomeworkRequest")
    public Result<Void> publishOrUpdateHomeworkRequest(@RequestBody String homeworkReq){
        HomeworkRequest homeworkRequest = JsonUtil.parseJsonWithGson(homeworkReq,HomeworkRequest.class);
        homeworkRequestService.save(homeworkRequest);
        return Result.success(null);
    }

    @GetMapping("homeworkRequest/{homeworkRequestId}")
    public Result<HomeworkRequestDto> getHomeworkRequestById(@PathVariable Integer homeworkRequestId){
        HomeworkRequestDto homeworkRequestDto = homeworkRequestService.getHomeworkRequestById(homeworkRequestId);
        return Result.success(homeworkRequestDto);
    }

    @GetMapping("lastestWeekHomeworkRequestByTeacher")
    public Result<List<HomeworkRequestDto>> getHomeRequestsByTeacherId(@PathVariable String teacherId){

        return Result.success(homeworkRequestService.getLastestWeekHWRByTeacherId(teacherId));
    }

}