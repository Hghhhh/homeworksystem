package com.hgh.homeworksystem.service;

import com.hgh.homeworksystem.dto.HomeworkRequestDto;
import com.hgh.homeworksystem.entity.HomeworkRequest;

import java.util.List;

public interface HomeworkRequestService {

    void save(HomeworkRequest homeworkRequest);

    HomeworkRequestDto getHomeworkRequestById(Integer id);

    List<HomeworkRequestDto> getLastestWeekHWRByTeacherId(String teacherId);

    List<HomeworkRequestDto> getAllHWRByTeacherId(String teacherId);

    List<HomeworkRequestDto> getLastestWeekHWRByClassId(Integer classId);

    List<HomeworkRequestDto> getAllHWRByClassId(Integer classId);


}
