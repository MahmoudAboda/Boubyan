/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Role {
    STUDENT("534fb61d-472f-48a6-a19a-222efc6b85c6","Student"," طالب");

    private final String id;
    private final String description;
    private final String descriptionAR;

    public static List<Role> getRoles(){
        return Arrays.stream(Role.values()).toList();
    }


    public static Role findById(String id){
       return Stream.of(Role.values()).filter(role -> role.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }


}
