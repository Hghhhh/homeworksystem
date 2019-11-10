package com.hgh.homeworksystem.service.impl;

import com.hgh.homeworksystem.dao.HomeworkDao;
import com.hgh.homeworksystem.dao.HomeworkRequestDao;
import com.hgh.homeworksystem.dto.HomeworkDto;
import com.hgh.homeworksystem.dto.HomeworkForStudentDto;
import com.hgh.homeworksystem.dto.HomeworkRequestDto;
import com.hgh.homeworksystem.dto.TeacherHomeworkDto;
import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.entity.User;
import com.hgh.homeworksystem.service.HomeworkService;
import com.hgh.homeworksystem.service.UserService;
import com.hgh.homeworksystem.util.JsonUtil;
import com.hgh.homeworksystem.util.SimHashUtil;
import com.hgh.homeworksystem.util.TimeUtil;
import com.hgh.homeworksystem.util.WordUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private UserService userService;

    @Autowired
    private HomeworkRequestDao homeworkRequestDao;

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

    private void dealNullValue(Homework entity, Homework source){
        //1.获取空属性并处理成null
        String[] nullProperties = getNullProperties(entity);
        //2.将非空属性覆盖到最新对象
        BeanUtils.copyProperties(entity,source, nullProperties);
    }

    @Override
    public void save(Homework homework) {
        if(homework.getId()!=null && homework.getId() != 0){
            Homework homework1 = homeworkDao.findById(homework.getId()).orElse(null);
            dealNullValue(homework,homework1);
            homework = homework1;
        }
        else{
            homework.setCreateTime(TimeUtil.getUnixTime());
        }
        try {
            if(homework.getContent()!=null) {
                //设置simhash
                homework.setSimhash(SimHashUtil.simHash(homework.getContent()));
            }
            if(homework.getEnclosure()!=null){
                homework.setSimhash(SimHashUtil.simHash(WordUtil.doc2String(homework.getEnclosure())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        homework.setUpdateTime(TimeUtil.getUnixTime());
        homeworkDao.save(homework);
    }

    @Override
    public Homework getById(Integer id) {
        return homeworkDao.findById(id).orElse(null);
    }

    @Override
    public TeacherHomeworkDto getDtoByRequestId(Integer requestId) {
        List<Homework> homeworks = homeworkDao.findByRequestId(requestId);
        List<HomeworkDto> homeworkDtos = new ArrayList<>(homeworks.size());
        List<HomeworkDto> simHomeworkDtos = null;
        String[] simhHomeworks = null;
        String simhHomework = homeworkRequestDao.findById(requestId).orElse(null).getSimHomework();
        if(simhHomework != null){
            simhHomeworks = simhHomework.split(",");
            simHomeworkDtos = new ArrayList<>(simhHomeworks.length);
        }
        for(Homework homework : homeworks){
            HomeworkDto homeworkDto = new HomeworkDto(homework);
            User user = userService.findByAccount(homework.getStudentId());
            user.setPassword(null);
            homeworkDto.setStudent(user);
            homeworkDto.setContent(null);
            homeworkDtos.add(homeworkDto);
            if(simhHomeworks != null){
                for(String simHomeworkId : simhHomeworks){
                    if(simHomeworkId.equals(homework.getId().toString())){
                        simHomeworkDtos.add(homeworkDto);
                    }
                }
            }
        }
        TeacherHomeworkDto teacherHomeworkDto = new TeacherHomeworkDto();
        teacherHomeworkDto.setHomeworkList(homeworkDtos);
        teacherHomeworkDto.setSimHomeworkList(simHomeworkDtos);
        return teacherHomeworkDto;
    }

    @Override
    public List<Homework> getByRequestId(Integer requestId) {
        List<Homework> homeworks = homeworkDao.findByRequestId(requestId);
        return homeworks;
    }

    @Override
    public HomeworkForStudentDto getByRequestIdAndStudent(Integer requestId, String student) {
        Homework homework = homeworkDao.findByRequestIdAndStudentId(requestId,student);
        homework.setSimhash(null);
        HomeworkRequest homeworkRequest = homeworkRequestDao.getOne(requestId);
        HomeworkRequestDto homeworkRequestDto = new HomeworkRequestDto(homeworkRequest);
        User teacher = userService.findByAccount(homeworkRequest.getTeacherId());
        teacher.setPassword(null);
        homeworkRequestDto.setTeacher(teacher);
        HomeworkForStudentDto homeworkForStudentDto = new HomeworkForStudentDto();
        homeworkForStudentDto.setHomework(homework);
        homeworkForStudentDto.setHomeworkRequest(homeworkRequestDto);
        return homeworkForStudentDto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGrade(Integer id, Integer grade, String remarks) {
        homeworkDao.updateGrade(id,grade,remarks);
    }
}
