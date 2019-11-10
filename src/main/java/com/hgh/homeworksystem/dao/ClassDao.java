package com.hgh.homeworksystem.dao;

import com.hgh.homeworksystem.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassDao extends JpaRepository<Class,Integer>{

    @Query(value = "select classId from tb_teacher_class where teacherId=?1", nativeQuery = true)
    List<Integer> findClassIdByTeacherId(String teacherId);

}
