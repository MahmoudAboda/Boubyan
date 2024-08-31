/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.mapper;

import com.oschool.student.dto.user.UserCreateDTO;
import com.oschool.student.dto.user.UserDTO;
import com.oschool.student.dto.user.UserResponseDTO;
import com.oschool.student.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    User toEntity(UserCreateDTO userDTO);


    UserDTO toDTO(User user);
    UserResponseDTO toResponseDTO(User user);




}
