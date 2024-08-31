package com.oschool.student.util;

import com.oschool.student.error.exceptions.AbstractStudentException;
import com.oschool.student.error.exceptions.AbstractTokenExpiredException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserUtils {

    /**
     * Converts an Object to a Long. Supports String and Double types.
     *
     * @param value The Object to be converted
     * @return The converted Long value
     * @throws IllegalArgumentException if the type of the value is unexpected
     */
    public static Long convertToLong(Object value) {
        try {
            // Check the type of the value and perform the appropriate conversion
            if (value instanceof String) {
                return Long.parseLong((String) value);
            } else if (value instanceof Double) {
                return ((Double) value).longValue();
            } else {
                throw new IllegalArgumentException("Unexpected type: " + value.getClass().getName());
            }
        } catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to convert value due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }
}
