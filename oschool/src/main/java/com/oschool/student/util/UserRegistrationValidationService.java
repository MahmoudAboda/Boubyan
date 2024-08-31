/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.util;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;


@Service

public class UserRegistrationValidationService {

    String passwordRegexPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W])\\S{8,32}$";
    //"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";


    public boolean passwordValid(String password){
            Pattern pattern = Pattern.compile(passwordRegexPattern);
            return pattern.matcher(password).matches();

    }


}
