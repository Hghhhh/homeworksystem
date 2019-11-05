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
        if(homeworkRequest.getId()!=null || homeworkRequest.getId() != 0){
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

    @Override
    public List<HomeworkRequestDto> getAllHWRByTeacherId(String teacherId) {
        List<HomeworkRequest> homeworkRequests = homeworkRequestDao.findByTeacherIdOrderByCreateTimeDesc(teacherId);
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

    @Override
    public List<HomeworkRequestDto> getAllHWRByClassId(Integer classId) {
        List<HomeworkRequest> homeworkRequests = homeworkRequestDao.findByClassIdOrderByCreateTimeDesc(classId);
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

    @Override
    public List<HomeworkRequestDto> getLastestWeekHWRByClassId(Integer classId) {
        Integer beginUnixTime = TimeUtil.getBeginUnixTimeOfWeek();
        Integer endUnixTime = TimeUtil.getEndUnixTimeOfWeek();
        List<HomeworkRequest> homeworkRequests = homeworkRequestDao.findByClassIdAndCreateTimeBetween(classId,beginUnixTime,endUnixTime);
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
