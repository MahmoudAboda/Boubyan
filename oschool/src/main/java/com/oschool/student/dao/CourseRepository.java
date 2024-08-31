/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.dao;

import com.oschool.student.dto.StudentCourseDTO;
import com.oschool.student.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {



    // Native query to delete a record from the course_student join table
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM course_student WHERE student_id = ?1 ", nativeQuery = true)
    void deleteStudentCourse(Long studentId);

    // Native query to insert a record into the course_student join table
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO course_student (student_id,course_id) VALUES (?1, ?2)", nativeQuery = true)
    void insertStudentCourse(Long studentId, Long courseId);

  // Native query to find all courses by student ID
  @Query("SELECT new com.oschool.student.dto.StudentCourseDTO(c.id, c.name, c.startDate, c.endDate, s.id, CONCAT(s.firstName, ' ', s.lastName), s.email) " +
          "FROM Course c " +
          "JOIN c.students s " +
          "WHERE s.id = :studentId")
  List<StudentCourseDTO> findStudentCoursesByStudentId(Long studentId);
}
