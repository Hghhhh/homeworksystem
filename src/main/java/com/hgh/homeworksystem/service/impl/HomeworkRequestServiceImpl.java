package com.hgh.homeworksystem.service.impl;

import com.hgh.homeworksystem.dao.ClassDao;
import com.hgh.homeworksystem.dao.HomeworkDao;
import com.hgh.homeworksystem.dao.HomeworkRequestDao;
import com.hgh.homeworksystem.dao.UserDao;
import com.hgh.homeworksystem.dto.HomeworkRequestDto;
import com.hgh.homeworksystem.dto.HomeworkRequestForStudentDto;
import com.hgh.homeworksystem.dto.UserDto;
import com.hgh.homeworksystem.entity.Class;
import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.entity.User;
import com.hgh.homeworksystem.service.HomeworkRequestService;
import com.hgh.homeworksystem.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.*;

@Service
public class HomeworkRequestServiceImpl implements HomeworkRequestService{
    @Autowired
    private HomeworkRequestDao homeworkRequestDao;

    @Autowired
    private ClassDao classDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HomeworkDao homeworkDao;

    /**
     * 获取对象的空属性
     */
    private static String[] getNullProperties(Object src) {
        //1.获取Bean
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        //2.获取Bean的属性描述
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        //3.获取Bean的空属性
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = srcBean.getPropertyValue(propertyName);
            if (StringUtils.isEmpty(propertyValue)) {
                srcBean.setPropertyValue(propertyName, null);
                properties.add(propertyName);
            }
        }
        return properties.toArray(new String[0]);
    }

    private void dealNullValue(HomeworkRequest entity, HomeworkRequest source){
        //1.获取空属性并处理成null
        String[] nullProperties = getNullProperties(entity);
        //2.将非空属性覆盖到最新对象
        BeanUtils.copyProperties(entity,source, nullProperties);
    }


    @Override
    public void save(HomeworkRequest homeworkRequest) {
        if(homeworkRequest.getId()!=null && homeworkRequest.getId() != 0){
            HomeworkRequest homeworkRequest1 = homeworkRequestDao.findById(homeworkRequest.getId()).orElse(null);
            dealNullValue(homeworkRequest,homeworkRequest1);
            homeworkRequest = homeworkRequest1;
        }
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
            teacher.setPassword(null);
            homeworkRequestDto.setTeacher(teacher);
            return homeworkRequestDto;
        }
    }

    @Override
    public List<HomeworkRequestDto> getLastestWeekHWRByTeacherId(String teacherId) {
        Integer beginUnixTime = TimeUtil.getBeginUnixTimeOfWeek();
        Integer endUnixTime = TimeUtil.getEndUnixTimeOfWeek();

        List<HomeworkRequest> homeworkRequests = homeworkRequestDao.findByTeacherIdAndCreateTimeBetweenOrderByCreateTime(teacherId,beginUnixTime,endUnixTime);
        List<HomeworkRequestDto> homeworkRequestDtos = new ArrayList<>();
        homeworkRequests.forEach(a->{
            HomeworkRequestDto homeworkRequestDto = new HomeworkRequestDto(a);
            Class cla = classDao.findById(a.getClassId()).orElse(null);
            homeworkRequestDto.setBelongClass(cla);
            User teacher = userDao.findById(a.getTeacherId()).orElse(null);
            teacher.setPassword(null);
            homeworkRequestDto.setTeacher(teacher);
            Integer studentNum = userDao.countByClassId(a.getClassId());
            Integer submitedNum = homeworkDao.countByRequestIdAndState(a.getId(),1);
            Integer checkedNum = homeworkDao.countByRequestIdAndState(a.getId(),2);
            homeworkRequestDto.setStudentNum(studentNum);
            homeworkRequestDto.setCheckedNum(checkedNum);
            homeworkRequestDto.setSubmitedNum(submitedNum + checkedNum);
            homeworkRequestDtos.add(homeworkRequestDto);
        });
        return homeworkRequestDtos;
    }

    @Override
    public List<HomeworkRequestDto> getAllHWRByTeacherId(String teacherId) {
        List<HomeworkRequest> homeworkRequests = homeworkRequestDao.findByTeacherIdAndCreateTimeGreaterThanOrderByCreateTimeDesc(teacherId, TimeUtil.getBeginUnixTimeOfWeek());
        List<HomeworkRequestDto> homeworkRequestDtos = new ArrayList<>();
        homeworkRequests.forEach(a->{
            HomeworkRequestDto homeworkRequestDto = new HomeworkRequestDto(a);
            Class cla = classDao.findById(a.getClassId()).orElse(null);
            homeworkRequestDto.setBelongClass(cla);
            User teacher = userDao.findById(a.getTeacherId()).orElse(null);
            teacher.setPassword(null);
            homeworkRequestDto.setTeacher(teacher);
            Integer studentNum = userDao.countByClassId(a.getClassId());
            Integer submitedNum = homeworkDao.countByRequestIdAndState(a.getId(),1);
            Integer checkedNum = homeworkDao.countByRequestIdAndState(a.getId(),2);
            homeworkRequestDto.setStudentNum(studentNum);
            homeworkRequestDto.setCheckedNum(checkedNum);
            homeworkRequestDto.setSubmitedNum(submitedNum + checkedNum);
            homeworkRequestDtos.add(homeworkRequestDto);
        });
        return homeworkRequestDtos;
    }

    @Override
    public List<HomeworkRequestForStudentDto> getAllHWRByClassId(Integer classId, String studentId) {
        List<HomeworkRequest> homeworkRequests = homeworkRequestDao.findByClassIdAndCreateTimeGreaterThanOrderByCreateTimeDesc(classId, TimeUtil.getBeginUnixTimeOfWeek());
        List<HomeworkRequestForStudentDto> homeworkRequestDtos = new ArrayList<>();
        homeworkRequests.forEach(a->{
            HomeworkRequestForStudentDto homeworkRequestDto = new HomeworkRequestForStudentDto();
            homeworkRequestDto.setRequestId(a.getId());
            homeworkRequestDto.setTitle(a.getTitle());
            Homework homework = homeworkDao.findByRequestIdAndStudentId(a.getId(),studentId);
            homeworkRequestDto.setDeadline(a.getDeadline());
            homeworkRequestDto.setState(0);
            if(homework != null){
                homeworkRequestDto.setGrade(homework.getGrade());
                homeworkRequestDto.setState(homework.getState());
            }
            homeworkRequestDtos.add(homeworkRequestDto);
        });
        return homeworkRequestDtos;
    }

    @Override
    public List<HomeworkRequest> getByStateAndDeadLine() {
        Integer time = TimeUtil.getUnixTime();
        return homeworkRequestDao.findByStateAndDeadlineLessThan(0,time);
    }

    @Override
    public void saveAll(List<HomeworkRequest> homeworkRequests) {
        for(HomeworkRequest homeworkRequest : homeworkRequests){
            homeworkRequestDao.save(homeworkRequest);
        }
    }

    @Override
    public List<HomeworkRequestForStudentDto> getLastestWeekHWRByClassId(Integer classId, String studentId) {
        Integer beginUnixTime = TimeUtil.getBeginUnixTimeOfWeek();
        Integer endUnixTime = TimeUtil.getEndUnixTimeOfWeek();
        List<HomeworkRequest> homeworkRequests = homeworkRequestDao.findByClassIdAndCreateTimeBetweenOrderByCreateTimeDesc(classId,beginUnixTime,endUnixTime);
        List<HomeworkRequestForStudentDto> homeworkRequestDtos = new ArrayList<>();
        homeworkRequests.forEach(a->{
            HomeworkRequestForStudentDto homeworkRequestDto = new HomeworkRequestForStudentDto();
            homeworkRequestDto.setRequestId(a.getId());
            homeworkRequestDto.setTitle(a.getTitle());
            Homework homework = homeworkDao.findByRequestIdAndStudentId(a.getId(),studentId);
            homeworkRequestDto.setDeadline(a.getDeadline());
            homeworkRequestDto.setState(0);
            if(homework != null){
                homeworkRequestDto.setGrade(homework.getGrade());
                homeworkRequestDto.setState(homework.getState());
            }
            homeworkRequestDtos.add(homeworkRequestDto);
        });
        return homeworkRequestDtos;
    }
}
