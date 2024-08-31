/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.service.impl;

import com.oschool.student.dao.CourseRepository;
import com.oschool.student.dao.StudentRepository;
import com.oschool.student.dto.StudentCourseDTO;
import com.oschool.student.dto.student.CachedStudentInfo;
import com.oschool.student.dto.student.StudentCreateDTO;
import com.oschool.student.dto.student.StudentResponseDTO;
import com.oschool.student.error.exceptions.AbstractStudentException;
import com.oschool.student.error.exceptions.AbstractTokenExpiredException;
import com.oschool.student.mapper.StudentMapper;
import com.oschool.student.model.Student;
import com.oschool.student.service.ICacheService;
import com.oschool.student.service.IStudentService;
import com.oschool.student.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.oschool.student.util.UserUtils.convertToLong;


@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseRepository courseRepository;
    private final JwtUtils jwtUtils;
    private final ICacheService cacheService;


    /**
     * Saves a new student entity based on the provided StudentCreateDTO.
     *
     * @param studentCreateDTO DTO containing student creation data
     * @return StudentResponseDTO containing the saved student data
     * @throws AbstractTokenExpiredException if the JWT token is expired
     * @throws AbstractStudentException for any other exceptions during the save process
     */
    public StudentResponseDTO save(StudentCreateDTO studentCreateDTO) {
        try {
            // Convert the DTO to a Student entity
            Student student = studentMapper.toEntity(studentCreateDTO);

            // Save the Student entity to the repository
            studentRepository.save(student);

            // Convert the saved Student entity to a StudentResponseDTO and return it
            return studentMapper.toResponseDTO(student);
        }
        catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        }
        catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to save due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }


    /**
     * Retrieves a list of courses associated with a student based on the provided JWT token.
     *
     * @param token JWT token containing student identification information
     * @return List of StudentCourseDTO containing course details for the student
     * @throws AbstractTokenExpiredException if the JWT token is expired
     * @throws AbstractStudentException for any other exceptions during the retrieval process
     */
    public List<StudentCourseDTO> findStudentCoursesByStudentId(String token) {
        try {
            Long studentId;
            // Extract student ID from the JWT token
            CachedStudentInfo cachedStudentInfo =
                    cacheService.getValueByKeyFromToCachedStudentInfoMap(convertToLong(jwtUtils.getStudentIdFromToken(token)));
            if (cachedStudentInfo != null && cachedStudentInfo.getId() != null) {
                studentId = cachedStudentInfo.getId();
            } else {
                String student = jwtUtils.getStudentIdFromToken(token);
                studentId = Long.parseLong(student);
            }

            // Fetch the list of courses for the student from the repository
            return courseRepository.findStudentCoursesByStudentId(studentId);

        } catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to retrieve student courses due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }







}
