package com.hgh.homeworksystem.service.impl;

import com.hgh.homeworksystem.dao.HomeworkDao;
import com.hgh.homeworksystem.entity.Homework;
import com.hgh.homeworksystem.entity.HomeworkRequest;
import com.hgh.homeworksystem.service.HomeworkService;
import com.hgh.homeworksystem.util.SimHashUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class HomeworkServiceImpl implements HomeworkService {

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

    private void dealNullValue(Homework entity, Homework source){
        //1.获取空属性并处理成null
        String[] nullProperties = getNullProperties(entity);
        //2.将非空属性覆盖到最新对象
        BeanUtils.copyProperties(entity,source, nullProperties);
    }

    @Override
    public void save(Homework homework) {
        if(homework.getId()!=null || homework.getId() != 0){
            Homework homework1 = homeworkDao.findById(homework.getId()).orElse(null);
            dealNullValue(homework,homework1);
            homework = homework1;
        }
        try {
            //设置simhash
            homework.setSimhash(SimHashUtil.simHash(homework.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeworkDao.save(homework);
    }

    @Override
    public Homework getByRequestId(Integer requestId) {
        return homeworkDao.findByRequestId(requestId);
    }
}
