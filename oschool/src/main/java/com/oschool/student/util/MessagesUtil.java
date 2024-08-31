/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.util;


import com.oschool.student.enumeration.AcceptHeaderLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessagesUtil {
    private final HttpServletRequest servletRequest;
    private final MessageSource messageSource;

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, new Locale(servletRequest.getHeader("Accept-Language")
                != null ? servletRequest.getHeader("Accept-Language") : AcceptHeaderLanguage.en.toString()));

    }
}
