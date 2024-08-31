package com.oschool.student.controller;

import com.oschool.student.dto.common.ResponsePayload;
import com.oschool.student.dto.user.UserCredentials;
import com.oschool.student.dto.user.UserLoginDTO;
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
@RequestMapping(UserController.PATH)
public class UserController {

    public static final String PATH="api/v1/user";
    private IUserService userService;


    @ApiOperation(value = "Refresh token for student")
    @GetMapping("refresh-token")
    public ResponseEntity<ResponsePayload> refreshTokenForStudent(@RequestHeader(name = "Authorization") String token,@RequestHeader(name = "refreshToken") String refreshToken) throws Exception {
        return new ResponseEntity<>(
                ResponsePayload.builder()
                        .date(LocalDateTime.now())
                        .content(Map.of("message","Token refreshed successfully.","refresh",userService.refreshTokenForStudent(token)))
                        .build(), HttpStatus.OK);
    }


    @ApiOperation(value = "Student registration")
    @PostMapping("register")
    public ResponseEntity<ResponsePayload>save(@RequestBody @Valid UserLoginDTO userLoginDTO) {

        return new ResponseEntity<ResponsePayload>(ResponsePayload.builder()
                .date(LocalDateTime.now())
                .content(Map.of("message","Student created successfully.","student", userService.registerStudent(userLoginDTO)))
                .build(), HttpStatus.CREATED);

    }


    @ApiOperation(value = "Student login")
    @PostMapping("/student/authenticate")
    public ResponseEntity<ResponsePayload> authenticateStudent(@RequestBody @Valid UserCredentials loginDto){
        return new ResponseEntity<>(ResponsePayload.builder()
                .date(LocalDateTime.now())
                .content(
                        Map.of("data",userService.authenticateStudent(loginDto))
                )
                .build(), HttpStatus.OK);
    }

}
