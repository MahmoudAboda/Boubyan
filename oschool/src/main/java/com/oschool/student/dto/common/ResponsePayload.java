/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.dto.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Map;

@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayload {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private Map<?,?> content;
    private Pageable pageable;
    /** error map will contain the following keys:
     * message: success operation message
     * errorMessage: failure operation message
     * errorDetails: guidance for the developers holds the exceptions stacktrace
     **/
    private Map<?,?> error;
    private Long totalElements;
    private Integer totalPages;
}
