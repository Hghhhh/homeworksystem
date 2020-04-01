package com.hgh.homeworksystem.controller;

import com.hgh.homeworksystem.dto.*;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.service.HomeworkRequestService;
import com.hgh.homeworksystem.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作业题目controller
 */
@RestController
public class HomeworkRequestController {

    @Autowired
    private HomeworkRequestService homeworkRequestService;

    @PostMapping("homeworkRequest")
    public Result<Void> publishOrUpdateHomeworkRequest(@RequestBody String homeworkSaveDto){
        HomeworkRequestSaveDto homeworkRequestDto = JsonUtil.parseJsonWithGson(homeworkSaveDto,HomeworkRequestSaveDto.class);
        HomeworkRequest homeworkRequest = new HomeworkRequest(homeworkRequestDto);
        if(homeworkRequestDto.getClassIds() == null){
            homeworkRequestService.save(homeworkRequest);
        }else{
            for(Integer classId : homeworkRequestDto.getClassIds()){
                homeworkRequest.setClassId(classId);
                homeworkRequest.setId(null);
                homeworkRequestService.save(homeworkRequest);
            }
        }
        return Result.success(null);
    }

    @GetMapping("homeworkRequest/{homeworkRequestId}")
    public Result<HomeworkRequestDto> getHomeworkRequestById(@PathVariable Integer homeworkRequestId){
        HomeworkRequestDto homeworkRequestDto = homeworkRequestService.getHomeworkRequestById(homeworkRequestId);
        return Result.success(homeworkRequestDto);
    }

    @GetMapping("homeworkRequestByTeacher")
    public Result<List<HomeworkRequestDto>> getLastestWeekHomeworkRequestsByTeacherId(@RequestParam String teacherId,@RequestParam Integer isLastWeek){
        List<HomeworkRequestDto> homeworkRequestDtos = null;
        if(isLastWeek.equals(0)){
            homeworkRequestDtos = homeworkRequestService.getAllHWRByTeacherId(teacherId);
        }else if(isLastWeek.equals(1)){
            homeworkRequestDtos = homeworkRequestService.getLastestWeekHWRByTeacherId(teacherId);
        }else{
            return Result.error(CodeMsg.PARAM_ERROR);
        }
        return Result.success(homeworkRequestDtos);
    }

    @GetMapping("homeworkRequestByClass")
    public Result<List<HomeworkRequestForStudentDto>> getLastestWeekHomeworkRequestByClassId(@RequestParam Integer classId, @RequestParam Integer isLastWeek, @RequestParam String studentId){
        List<HomeworkRequestForStudentDto> homeworkRequestDtos = null;
        if(isLastWeek.equals(0)){
            homeworkRequestDtos = homeworkRequestService.getAllHWRByClassId(classId, studentId);
        }else if(isLastWeek.equals(1)){
            homeworkRequestDtos = homeworkRequestService.getLastestWeekHWRByClassId(classId, studentId);
        }else{
            return Result.error(CodeMsg.PARAM_ERROR);
        }
        return Result.success(homeworkRequestDtos);
    }


}