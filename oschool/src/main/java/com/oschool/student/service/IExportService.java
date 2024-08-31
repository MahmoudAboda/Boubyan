/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.service;

import java.io.ByteArrayInputStream;

public interface IExportService {

    ByteArrayInputStream exportStudentCoursesReport(String token);
}
