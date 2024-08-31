/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.service;

import com.oschool.student.dto.common.JwtResponse;
import com.oschool.student.dto.student.StudentLoginResponse;
import com.oschool.student.dto.user.UserCredentials;
import com.oschool.student.dto.user.UserLoginDTO;

public interface IUserService {

    boolean registerStudent(UserLoginDTO userLoginDto);
    StudentLoginResponse authenticateStudent(UserCredentials userCredentials );
    JwtResponse refreshTokenForStudent(String jwt) throws Exception;
    JwtResponse keepUserSession10Minutes(String jwt ) throws Exception;

}
