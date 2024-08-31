/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AppClient {
    MS_STUDENT("a9f4e75b-4f0a-4a3b-8ce3-68bfe216db77");


    private final String id;

    public static AppClient findById(String id){
        return Arrays.stream(AppClient.values()).filter(appClient -> appClient.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
