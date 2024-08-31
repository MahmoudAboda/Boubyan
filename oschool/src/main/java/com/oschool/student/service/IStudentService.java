/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.service;

import com.oschool.student.dto.StudentCourseDTO;
import com.oschool.student.dto.student.StudentCreateDTO;
import com.oschool.student.dto.student.StudentResponseDTO;
import java.util.List;


public interface IStudentService {


    StudentResponseDTO save(StudentCreateDTO studentCreateDTO);
    List<StudentCourseDTO> findStudentCoursesByStudentId(String token);

}
