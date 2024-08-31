/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.service;

import com.oschool.student.dto.student.CachedStudentInfo;

public interface ICacheService {

    void saveKeyValueToCachedStudentInfoMap(Long key, CachedStudentInfo value);

    CachedStudentInfo getValueByKeyFromToCachedStudentInfoMap(Long key);
}
