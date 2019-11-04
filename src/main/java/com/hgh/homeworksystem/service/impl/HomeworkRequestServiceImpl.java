package com.hgh.homeworksystem.service.impl;

import com.hgh.homeworksystem.dao.ClassDao;
import com.hgh.homeworksystem.dao.HomeworkRequestDao;
import com.hgh.homeworksystem.dao.UserDao;
import com.hgh.homeworksystem.dto.HomeworkRequestDto;
import com.hgh.homeworksystem.entity.Class;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.entity.User;
import com.hgh.homeworksystem.service.HomeworkRequestService;
import com.hgh.homeworksystem.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeworkRequestServiceImpl implements HomeworkRequestService{
    @Autowired
    private HomeworkRequestDao homeworkRequestDao;

    @Autowired
    private ClassDao classDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void save(HomeworkRequest homeworkRequest) {
        homeworkRequestDao.save(homeworkRequest);
    }

    @Override
    public HomeworkRequestDto getHomeworkRequestById(Integer id){
        HomeworkRequest homeworkRequest = homeworkRequestDao.findById(id).orElse(null);
        if(homeworkRequest == null){
            return null;
        }else{
            HomeworkRequestDto homeworkRequestDto = new HomeworkRequestDto(homeworkRequest);
            Class cla = classDao.findById(homeworkRequest.getClassId()).orElse(null);
            homeworkRequestDto.setBelongClass(cla);
            User teacher = userDao.findById(homeworkRequest.getTeacherId()).orElse(null);
            homeworkRequestDto.setTeacher(teacher);
            return homeworkRequestDto;
        }
    }

    @Override
    public List<HomeworkRequestDto> getLastestWeekHWRByTeacherId(String teacherId) {
        Integer beginUnixTime = TimeUtil.getBeginUnixTimeOfWeek();
        Integer endUnixTime = TimeUtil.getEndUnixTimeOfWeek();
        List<HomeworkRequest> homeworkRequests = homeworkRequestDao.findByTeacherIdAndCreateTimeBetween(teacherId,beginUnixTime,endUnixTime);
        List<HomeworkRequestDto> homeworkRequestDtos = new ArrayList<>();
        homeworkRequests.forEach(a->{
            HomeworkRequestDto homeworkRequestDto = new HomeworkRequestDto(a);
            Class cla = classDao.findById(a.getClassId()).orElse(null);
            homeworkRequestDto.setBelongClass(cla);
            User teacher = userDao.findById(a.getTeacherId()).orElse(null);
            homeworkRequestDto.setTeacher(teacher);
            homeworkRequestDtos.add(homeworkRequestDto);
        });
        return homeworkRequestDtos;
    }
}
