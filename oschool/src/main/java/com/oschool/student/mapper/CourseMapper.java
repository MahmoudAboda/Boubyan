/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.mapper;

import com.oschool.student.dto.course.CourseCreateDTO;
import com.oschool.student.dto.course.CourseDTO;
import com.oschool.student.dto.course.CourseResponseDTO;
import com.oschool.student.model.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toEntity(CourseDTO courseDTO);

    Course toEntity(CourseCreateDTO courseCreateDTO);

    CourseDTO toDTO(Course course);

    CourseResponseDTO toResponseDTO(Course course);


    List<CourseResponseDTO> toResponseDTOList(List<Course> courses);

}
