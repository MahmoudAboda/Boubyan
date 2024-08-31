/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.controller;


import com.oschool.student.dto.common.ResponsePayload;
import com.oschool.student.dto.course.CourseCreateDTO;
import com.oschool.student.dto.course.CourseDTO;
import com.oschool.student.service.ICourseService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping(CourseController.PATH)
public class CourseController {
public static final String PATH="api/v1/course";
private ICourseService courseService;

    @ApiOperation(value = "Create new course")
    @PostMapping
    public ResponseEntity<ResponsePayload> save(@RequestBody @Valid CourseCreateDTO courseCreateDTO,  @RequestHeader(name = "Authorization") String token) {

        return new ResponseEntity<ResponsePayload>(ResponsePayload.builder()
                .date(LocalDateTime.now())
                .content(Map.of("message", "Course created successfully.", "course", courseService.save(courseCreateDTO, token)))
                .build(), HttpStatus.CREATED);

    }



    @ApiOperation(value = "Retrieve course by id")
    @GetMapping("by-id")
    public ResponseEntity<ResponsePayload> findCourseById(@RequestParam Long id,  @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(
                ResponsePayload.builder()
                        .date(LocalDateTime.now())
                        .content(Map.of("message","Course retrieved successfully.","course",courseService.getById(id,token)))
                        .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve All Courses.")
    @GetMapping
    public ResponseEntity<ResponsePayload> getAllCourses( Pageable pageable, @RequestHeader(name = "Authorization") String token) {
            return new ResponseEntity<>(ResponsePayload.builder()
                    .date(LocalDateTime.now())
                    .content(Map.of("message","Courses retrieved successfully.","course",courseService.getAllCourses(pageable) ))
                    .build(), HttpStatus.OK);

    }

    @ApiOperation(value = "delete course by id")
    @DeleteMapping
    public ResponseEntity<ResponsePayload>deleteById(@RequestParam  Long id, @RequestHeader(name = "Authorization") String token) {

        return new ResponseEntity<ResponsePayload>(ResponsePayload.builder()
                .date(LocalDateTime.now())
                .content(Map.of("message","Course deleted successfully.","course", courseService.deleteById(id,token)))
                .build(), HttpStatus.OK);

    }


    @ApiOperation(value = "delete student courses")
    @DeleteMapping("/delete-student-courses")
    public ResponseEntity<ResponsePayload>deleteStudentCourse(@RequestHeader(name = "Authorization") String token) {

        return new ResponseEntity<ResponsePayload>(ResponsePayload.builder()
                .date(LocalDateTime.now())
                .content(Map.of("message","Student courses deleted successfully.","course", courseService.deleteStudentCourse(token)))
                .build(), HttpStatus.OK);

    }


    @ApiOperation(value = "Add student courses")
    @PostMapping("add-student-course")
    public ResponseEntity<ResponsePayload>addStudentCourse( @RequestParam  Long courseId, @RequestHeader(name = "Authorization") String token) {

        return new ResponseEntity<ResponsePayload>(ResponsePayload.builder()
                .date(LocalDateTime.now())
                .content(Map.of("message","Student courses inserted successfully.","course", courseService.addStudentCourse(courseId,token)))
                .build(), HttpStatus.OK);

    }







}
