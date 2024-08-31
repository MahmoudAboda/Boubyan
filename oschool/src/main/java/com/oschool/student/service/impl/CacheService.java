/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.service.impl;

import com.oschool.student.dto.student.CachedStudentInfo;
import com.oschool.student.service.ICacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheService implements ICacheService {
    private final RedissonClient redissonClient;

    /**
     * Saves a key-value pair to a cached student info map using Redisson for distributed caching.
     *
     * @param key   The key to be saved in the cache.
     * @param value The CachedStudentInfo object to be associated with the key.
     */
    public void saveKeyValueToCachedStudentInfoMap(Long key, CachedStudentInfo value) {
        try {
            // Log the start of the method with the key parameter
            log.info("Start calling save key value to cached student info map service with parameters key: {}", key);

            // Retrieve the Redisson map named "studentInfoMap" for caching
            RMap<Long, CachedStudentInfo> cachedEmployerInfoRMap = redissonClient.getMap("studentInfoMap");

            // Save the key-value pair in the Redisson map
            cachedEmployerInfoRMap.put(key, value);
        } catch (Exception e) {
            // Log an error message if an exception occurs
            log.error("Unable to call save key value to cached student info map due to {}", e.getMessage());

            // Print the stack trace for debugging purposes
            e.printStackTrace();
        }
    }


    /**
     * Retrieves a CachedStudentInfo object from the cached student info map using Redisson.
     *
     * @param key The key for which to retrieve the CachedStudentInfo object.
     * @return The CachedStudentInfo object associated with the key, or null if not found or an error occurs.
     */
    public CachedStudentInfo getValueByKeyFromToCachedStudentInfoMap(Long key) {
        try {
            // Log the start of the method with the key parameter
            log.info("Start calling get key value from cached student info map service with parameters key: {}", key);

            // Retrieve the Redisson map named "studentInfoMap" for caching
            RMap<String, CachedStudentInfo> cachedEmployerInfoRMap = redissonClient.getMap("studentInfoMap");

            // Get the value associated with the key from the Redisson map
            return cachedEmployerInfoRMap.get(key);
        } catch (Exception e) {
            // Log an error message if an exception occurs
            log.error("Unable to call get key value from cached student info map due to {}", e.getMessage());

            // Print the stack trace for debugging purposes
            e.printStackTrace();

            // Return null if an error occurs
            return null;
        }
    }
}
