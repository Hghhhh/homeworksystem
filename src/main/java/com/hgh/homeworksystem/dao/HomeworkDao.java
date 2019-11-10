package com.hgh.homeworksystem.dao;

import com.hgh.homeworksystem.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeworkDao extends JpaRepository<Homework,Integer> {


    List<Homework> findByRequestId(Integer requestId);

    Homework findByRequestIdAndStudentId(Integer requestId,String account);

    @Modifying
    @Query(value = "update tb_homework set grade=?2,remarks=?3,state=2 where id=?1",nativeQuery = true)
    void updateGrade(Integer id, Integer grade, String remarks);

    Integer countByRequestIdAndState(Integer requestId, Integer state);
}
