package com.hgh.homeworksystem.controller;

import com.hgh.homeworksystem.dto.*;
import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.service.HomeworkRequestService;
import com.hgh.homeworksystem.service.HomeworkService;
import com.hgh.homeworksystem.util.JsonUtil;
import com.hgh.homeworksystem.util.SimHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private HomeworkRequestService homeworkRequestService;

    private static final int DIFF = 10;

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

    @GetMapping("simHomework/{requestId}")
    public Result<List<HomeworkDto>> getSimHomework(@PathVariable Integer requestId){
        HomeworkRequestDto homeworkRequest = homeworkRequestService.getHomeworkRequestById(requestId);
        homeworkRequest.setState(1);
        List<HomeworkDto> simHomeworks = new ArrayList<>();
        List<Homework> homeworks = homeworkService.getByRequestId(homeworkRequest.getId());
        if(homeworks == null || homeworks.isEmpty()){
            return Result.success(simHomeworks);
        }
        for(int i=0; i<homeworks.size()-1; i++){
            for(int j=i+1;j<homeworks.size(); j++){
                List<BigInteger> list1 = SimHashUtil.subByDistance(homeworks.get(i).getSimhash(),DIFF);
                List<BigInteger> list2 = SimHashUtil.subByDistance(homeworks.get(j).getSimhash(),DIFF);
                if(isSimilar(list1,list2)){
                    simHomeworks.add(new HomeworkDto(homeworks.get(i)));
                    simHomeworks.add(new HomeworkDto(homeworks.get(j)));
                }
            }
        }
        return Result.success(simHomeworks);
    }

    private static boolean isSimilar(List<BigInteger> list1, List<BigInteger> list2){
        if(list1==null || list2==null){
            if(list1 == null && list2 == null){
                return true;
            }else{
                return false;
            }
        }
        for(int i = 0; i<list1.size(); i++){
            for(int j = 0; j < list2.size(); j++){
                if(list1.get(i).equals(list2.get(j))){
                    return true;
                }
            }
        }
        return false;
    }

}
