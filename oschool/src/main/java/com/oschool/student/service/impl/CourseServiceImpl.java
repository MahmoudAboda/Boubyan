/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.service.impl;


import com.oschool.student.dao.CourseRepository;
import com.oschool.student.dto.course.CourseCreateDTO;
import com.oschool.student.dto.course.CourseDTO;
import com.oschool.student.dto.course.CourseResponseDTO;
import com.oschool.student.error.exceptions.AbstractStudentException;
import com.oschool.student.error.exceptions.AbstractTokenExpiredException;
import com.oschool.student.mapper.CourseMapper;
import com.oschool.student.model.Course;
import com.oschool.student.service.ICourseService;
import com.oschool.student.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final JwtUtils jwtUtils;


    /**
     * Retrieves a CourseDTO by its ID.
     * This method fetches the course from the repository and converts it to a DTO.
     *
     * @param id    The ID of the course to be retrieved.
     * @param token The token used for authorization (currently not used in the method).
     * @return The CourseDTO object corresponding to the given ID.
     * @throws AbstractTokenExpiredException if the token is expired.
     * @throws AbstractStudentException      for other exceptions encountered during the operation.
     */
    public CourseDTO getById(Long id, String token) {
        try {
            // Log the start of the method with the provided course ID
            log.info("Start calling get course by id with parameters course id: {}", id);

            // Fetch the course entity by its ID from the repository and convert it to a DTO
            return courseMapper.toDTO(courseRepository.findById(id).get());
        } catch (ExpiredJwtException e) {
            // Handle case where the token is expired by throwing a specific exception
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log an error message and throw a custom exception for other errors
            log.error("Unable to call get course by id due to: [{}].", e.getMessage());
            throw new AbstractStudentException(e.getMessage());
        }
    }



    /**
     * Retrieves all courses and returns them as a list of CourseResponseDTO.
     * Currently fetches all courses without using the pageable parameter.
     *
     * @param pageable The pagination information (currently not used in the method).
     * @return A list of CourseResponseDTO objects representing all courses.
     * @throws AbstractTokenExpiredException if the token is expired.
     * @throws AbstractStudentException      for other exceptions encountered during the operation.
     */
    public List<CourseResponseDTO> getAllCourses(Pageable pageable) {
        try {
            // Log the start of the method
            log.info("Start calling get all courses");

            // Fetch all course entities from the repository
            List<Course> course = courseRepository.findAll();

            // Convert the list of course entities to a list of CourseResponseDTOs
            return courseMapper.toResponseDTOList(course);

        } catch (ExpiredJwtException e) {
            // Handle case where the token is expired by throwing a specific exception
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log an error message and throw a custom exception for other errors
            log.error("Unable to call get all courses due to: [{}].", e.getMessage());
            throw new AbstractStudentException(e.getMessage());
        }
    }


    /**
     * Deletes a course by its ID.
     * If the course is successfully deleted, it returns true.
     * Handles exceptions related to token expiration and other potential errors.
     *
     * @param id    The ID of the course to be deleted.
     * @param token The token used for authorization (currently not used in the method).
     * @return true if the course was successfully deleted; otherwise, throws an exception.
     * @throws AbstractTokenExpiredException if the token is expired.
     * @throws AbstractStudentException      for other exceptions encountered during the operation.
     */
    public boolean deleteById(Long id, String token) {
        try {
            // Log the start of the delete operation with the provided course ID
            log.info("Delete course service started with parameters: [{}].", id);

            // Delete the course by its ID from the repository
            courseRepository.deleteById(id);

            // Return true indicating successful deletion
            return true;
        } catch (ExpiredJwtException e) {
            // Handle case where the token is expired by throwing a specific exception
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (EmptyResultDataAccessException e) {
            // Handle case where the course ID does not exist by logging the error and throwing a custom exception
            log.error("Course with id: [{}] not found for deletion.", id);
            throw new AbstractStudentException("Course not found");
        } catch (Exception e) {
            // Log an error message and throw a custom exception for other errors
            log.error("Unable to delete course with id: [{}] due to: [{}].", id, e.getMessage());
            throw new AbstractStudentException(e.getMessage());
        }
    }

    /**
     * Saves a new course using the provided CourseCreateDTO.
     * Converts the DTO to an entity, saves it to the repository, and returns the saved course as a CourseDTO.
     *
     * @param courseCreateDTO The DTO containing the course details to be saved.
     * @param token           The token used for authorization (currently not used in the method).
     * @return The saved CourseDTO object.
     * @throws AbstractTokenExpiredException if the token is expired.
     * @throws AbstractStudentException      for other exceptions encountered during the save operation.
     */
    public CourseDTO save(CourseCreateDTO courseCreateDTO, String token) {
        try {
            // Log the start of the save operation with the provided course name
            log.info("Start calling save course with parameters course name: {}", courseCreateDTO.getName());

            // Convert the CourseCreateDTO to a Course entity, save it, and convert the saved entity to CourseDTO
            return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(courseCreateDTO)));
        } catch (ExpiredJwtException e) {
            // Handle case where the token is expired by throwing a specific exception
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log an error message and throw a custom exception for other errors
            log.error("Unable to save course with name: [{}] due to: [{}].", courseCreateDTO.getName(), e.getMessage());
            throw new AbstractStudentException(e.getMessage());
        }
    }


    /**
     * Deletes a student's association with a course using the provided course ID.
     * Retrieves the student ID from the token and removes the association between the student and the course.
     *
     * @param token    The token used for authorization, from which the student ID is extracted.
     * @return A confirmation message indicating successful deletion.
     * @throws AbstractTokenExpiredException if the token is expired.
     * @throws AbstractStudentException      for other exceptions encountered during the deletion operation.
     */
    public String deleteStudentCourse( String token) {
        try {
            // Log the start of the delete operation with the provided course ID
            log.info("Start calling delete student id: {}", jwtUtils.getStudentIdFromToken(token));

            // Extract the student ID from the token and delete the course association for that student
            Long studentId = Long.parseLong(jwtUtils.getStudentIdFromToken(token));
            courseRepository.deleteStudentCourse(studentId);

            // Return a success message indicating that the course was deleted
            return "Deleted successfully";
        } catch (ExpiredJwtException e) {
            // Handle case where the token is expired by throwing a specific exception
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log an error message and throw a custom exception for other errors
            log.error("Unable to delete course for student with id: [{}] due to: [{}].", jwtUtils.getStudentIdFromToken(token), e.getMessage());
            throw new AbstractStudentException(e.getMessage());
        }
    }


    /**
     * Adds a course to a student's list of courses.
     * Retrieves the student ID from the token and associates the student with the specified course.
     *
     * @param courseId The ID of the course to be added to the student's course list.
     * @param token    The token used for authorization, from which the student ID is extracted.
     * @return A confirmation message indicating successful insertion.
     * @throws AbstractTokenExpiredException if the token is expired.
     * @throws AbstractStudentException      for other exceptions encountered during the insertion operation.
     */
    public String addStudentCourse(Long courseId, String token) {
        try {
            // Log the start of the add operation with the provided course ID
            log.info("Start calling add course to student with parameters course id: {}", courseId);

            // Extract the student ID from the token and add the course association for that student
            Long studentId = Long.parseLong(jwtUtils.getStudentIdFromToken(token));
            courseRepository.insertStudentCourse(studentId, courseId);

            // Return a success message indicating that the course was added
            return "Inserted successfully";
        } catch (ExpiredJwtException e) {
            // Handle case where the token is expired by throwing a specific exception
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            if(e.getMessage().contains("SQL [n/a"))
                throw new AbstractStudentException("This course exists for same student");
            // Log an error message and throw a custom exception for other errors
            log.error("Unable to add course to student with course id: [{}] due to: [{}].", courseId, e.getMessage());
            throw new AbstractStudentException(e.getMessage());
        }
    }




}
