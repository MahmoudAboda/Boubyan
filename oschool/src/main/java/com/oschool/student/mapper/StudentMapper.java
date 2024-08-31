/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */


package com.oschool.student.mapper;

import com.oschool.student.dto.student.StudentCreateDTO;
import com.oschool.student.dto.student.StudentDTO;
import com.oschool.student.dto.student.StudentResponseDTO;
import com.oschool.student.model.Student;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentDTO studentDTO);

    Student toEntity(StudentCreateDTO studentCreateDTO);

    StudentDTO toDTO(Student student);

    StudentResponseDTO toResponseDTO(Student student);


}
