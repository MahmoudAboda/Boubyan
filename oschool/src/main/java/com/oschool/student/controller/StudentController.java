/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.controller;


import com.oschool.student.dto.common.ResponsePayload;
import com.oschool.student.dto.user.UserCredentials;
import com.oschool.student.dto.user.UserLoginDTO;
import com.oschool.student.service.IStudentService;
import com.oschool.student.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping(StudentController.PATH)
public class StudentController {
public static final String PATH="api/v1/student";
private IStudentService  studentService;




    @ApiOperation(value = "Retrieve student by id")
    @GetMapping("get-student-courses")
    public ResponseEntity<ResponsePayload> findStudentCoursesByStudentId(@RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(
                ResponsePayload.builder()
                        .date(LocalDateTime.now())
                        .content(Map.of("message","Student retrieved successfully.","student",studentService.findStudentCoursesByStudentId(token)))
                        .build(),HttpStatus.OK);
    }






}
