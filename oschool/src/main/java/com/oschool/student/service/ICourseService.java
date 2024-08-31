/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.service;

import com.oschool.student.dto.course.CourseCreateDTO;
import com.oschool.student.dto.course.CourseDTO;
import com.oschool.student.dto.course.CourseResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICourseService {
    CourseDTO getById(Long id,String token);

    List<CourseResponseDTO> getAllCourses(Pageable pageable);

    boolean deleteById(Long id, String token);

    CourseDTO save(CourseCreateDTO courseCreateDTO, String token);


    String deleteStudentCourse(String token);

    String addStudentCourse( Long courseId,String token);
}
