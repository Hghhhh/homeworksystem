package com.hgh.homeworksystem.dao;

import com.hgh.homeworksystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User,String>{

    @Modifying
    @Query(value = "insert into tb_teacher_class VALUES (?1,?2)",nativeQuery = true)
    void insertTeacherClass(String teacherId,Integer classId);
}

