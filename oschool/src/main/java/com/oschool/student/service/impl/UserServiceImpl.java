/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.service.impl;


import com.oschool.student.dao.StudentRepository;
import com.oschool.student.dao.UserRepository;
import com.oschool.student.dto.common.JwtResponse;
import com.oschool.student.dto.student.CachedStudentInfo;
import com.oschool.student.dto.student.StudentCreateDTO;
import com.oschool.student.dto.student.StudentLoginInformation;
import com.oschool.student.dto.student.StudentLoginResponse;
import com.oschool.student.dto.user.UserCreateDTO;
import com.oschool.student.dto.user.UserCredentials;
import com.oschool.student.dto.user.UserLoginDTO;
import com.oschool.student.dto.user.UserResponseDTO;
import com.oschool.student.enumeration.Role;
import com.oschool.student.error.exceptions.AbstractStudentException;
import com.oschool.student.error.exceptions.AbstractTokenExpiredException;
import com.oschool.student.mapper.UserMapper;
import com.oschool.student.model.Student;
import com.oschool.student.model.User;
import com.oschool.student.service.ICacheService;
import com.oschool.student.service.IStudentService;
import com.oschool.student.service.IUserService;
import com.oschool.student.util.JwtUtils;
import com.oschool.student.util.MessagesUtil;
import com.oschool.student.util.UserRegistrationValidationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.oschool.student.enumeration.Role.STUDENT;
import static com.oschool.student.security.JwtEncryptionUtil.decrypt;
import static com.oschool.student.util.ImportUtil.isValidEmail;
import static com.oschool.student.util.UserUtils.convertToLong;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final MessagesUtil messagesUtil;
    private final UserRegistrationValidationService userRegistrationValidationService;
    private final UserMapper userMapper;
    private final IStudentService studentService;
    private final StudentRepository studentRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ICacheService cacheService;


    public boolean registerStudent(UserLoginDTO userLoginDto) {
        try {
            UserResponseDTO userResponse = createAndSaveUser(userLoginDto);
            registerStudent(userLoginDto, userResponse.getId());
            return true;
        } catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to register student due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }
    /**
     * Creates a User entity from the provided UserLoginDTO and saves it.
     *
     * @param userLoginDto The DTO containing user login details
     * @return The UserResponseDTO with the saved user details
     */
    private UserResponseDTO createAndSaveUser(UserLoginDTO userLoginDto) {
        try {

            User user = User.builder()
                    .password(userLoginDto.getPassword())
                    .email(userLoginDto.getEmail())
                    .role(userLoginDto.getRole())
                    .build();

            validateUser(user);

            UserCreateDTO userCreateDTO = UserCreateDTO.builder()
                    .role(userLoginDto.getRole())
                    .firstName(userLoginDto.getFirstName())
                    .lastName(userLoginDto.getLastName())
                    .password(userLoginDto.getPassword())
                    .email(userLoginDto.getEmail())
                    .build();

            return save(userCreateDTO);
        }catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to register student due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }

    /**
     * Registers a student using the provided UserLoginDTO and user ID.
     *
     * @param userLoginDto The DTO containing user login details
     * @param userId The ID of the user to associate with the student
     */
    private void registerStudent(UserLoginDTO userLoginDto, Long userId) {
        try {

            studentService.save(StudentCreateDTO.builder()
                    .role(userLoginDto.getRole())
                    .firstName(userLoginDto.getFirstName())
                    .lastName(userLoginDto.getLastName())
                    .userId(userId)
                    .email(userLoginDto.getEmail())
                    .build());
        }
        catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to register student due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }

    /**
     * Saves a new user entity based on the provided UserCreateDTO.
     *
     * @param userCreateDTO The DTO containing user details to be saved
     * @return The UserResponseDTO representing the saved user
     * @throws AbstractTokenExpiredException if the JWT token is expired
     * @throws AbstractStudentException for other errors during user saving
     */
    public UserResponseDTO save(UserCreateDTO userCreateDTO) {
        try {
            // Convert UserCreateDTO to User entity
            User user = userMapper.toEntity(userCreateDTO);

            // Encode the user's password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Set default role if none is provided
            if (userCreateDTO.getRole() == null) {
                user.setRole(Role.STUDENT); // Ensure you have a Role enum or constant
            }

            // Save the User entity to the repository
            userRepository.save(user);

            // Convert the saved User entity to UserResponseDTO and return
            return userMapper.toResponseDTO(user);

        } catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to save user due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }

    /**
     * Validates the provided User entity by performing necessary checks.
     *
     * @param user The User entity to be validated
     * @throws AbstractTokenExpiredException if the JWT token is expired
     * @throws AbstractStudentException for other validation errors
     */
    public void validateUser(User user) {
        try {
            // Check if a user with the same email already exists
            userExistsByEmailValidation(user);

            // Validate the user's password
            userIsPasswordValid(user);

        } catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to validate user due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }


    /**
     * Validates if a user with the given email already exists in the repository.
     *
     * @param user The User entity containing the email to be checked
     * @throws RuntimeException if a user with the same email already exists
     * @throws AbstractTokenExpiredException if the JWT token is expired
     * @throws AbstractStudentException for other validation errors
     */
    public void userExistsByEmailValidation(User user) {
        try {
            // Retrieve user by email ignoring case
            Optional<User> existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());

            // Check if a user with the same email already exists
            if (existingUser.isPresent()) {
                // Throw an exception if the email already exists
                throw new RuntimeException(messagesUtil.getMessage("email.already.exist.msg"));
            }

        } catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to validate user email due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }



    public void userIsPasswordValid(User user){
        if(null != user.getPassword() && !userRegistrationValidationService.passwordValid(user.getPassword()))
            throw new RuntimeException(messagesUtil.getMessage("password.invalid.msg"));


    }

    /**
     * Authenticates a student based on the provided credentials.
     *
     * @param userCredentials The credentials provided by the user (email and password)
     * @return StudentLoginResponse containing student details and a JWT token if authentication is successful
     * @throws RuntimeException if the user credentials are invalid
     * @throws AbstractTokenExpiredException if the JWT token is expired
     * @throws AbstractStudentException for other authentication errors
     */
    public StudentLoginResponse authenticateStudent(UserCredentials userCredentials) {
        try {
            // Load user details by email
            User user = loadUserByEmail(userCredentials.getEmail());

            // Check if the provided password matches the stored password
            boolean matches = passwordEncoder.matches(userCredentials.getPassword(), user.getPassword());

            if (matches) {
                StudentLoginInformation studentData;
                // Check if the user role is STUDENT
                if (user.getRole() != null && user.getRole().equals(STUDENT)) {
                    // Fetch student login details
                    studentData = getStudentLoginDetails(user.getId());

                    assert studentData != null; // Ensure that student data is not null

                    // Set user role from student data if not already set
                    user.setRole(user.getRole() != null ? user.getRole() : studentData.getRole());

                    // Generate a JWT token for the student
                    String token = jwtUtils.generateJwtTokenForStudent(
                            user.getEmail(),
                            studentData.getFirstName() + " " + studentData.getLastName(),
                            user.getAuthorities(),
                            studentData.getRole().toString(),
                            user.getId(),
                            studentData.getId()
                    );

                    // Cache student info asynchronously
                    new Thread(() -> {
                        cacheService.saveKeyValueToCachedStudentInfoMap(
                                studentData.getId(),
                                CachedStudentInfo.builder()
                                        .email(studentData.getEmail())
                                        .role(studentData.getRole())
                                        .id(studentData.getId())
                                        .firstName(studentData.getFirstName())
                                        .lastName(studentData.getLastName())
                                        .build()
                        );
                    }).start();

                    // Return response with student data and encrypted token
                    return StudentLoginResponse.builder()
                            .studentLoginInformation(studentData)
                            .token(token)
                            .build();
                }
            }
            // Throw exception if credentials are invalid
            throw new RuntimeException(messagesUtil.getMessage("invalid.user.credentials.msg"));

        } catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to authenticate student due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }


    /**
     * Refreshes the JWT token for a student based on the provided token.
     *
     * @param jwt The JWT token to be refreshed
     * @return JwtResponse containing the new JWT token
     * @throws AbstractTokenExpiredException if the JWT token is expired
     * @throws AbstractStudentException for any other exceptions during token refresh
     */
    public JwtResponse refreshTokenForStudent(String jwt) throws Exception {
        try {
            // Decrypt and parse the JWT token
            Map<String, Object> expectedMap;
            try {
                jwt = decrypt(jwt);
                expectedMap = jwtUtils.parseToken(jwt);
            } catch (Exception e) {
                // Handle cases where the token is invalid or decryption fails
                throw new RuntimeException("Invalid Token!");
            }

            // Extract user ID from the token and convert to long
            String userIdString = expectedMap.get("userId").toString();
            double userIdDouble = Double.parseDouble(userIdString);
            long userId = (long) userIdDouble;

            // Find the user by ID from the repository
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if user is found, if not throw an exception
            if (user == null) {
                throw new RuntimeException("Invalid user");
            }

            // Generate a new JWT token with refreshed information
            String token = jwtUtils.doGenerateRefreshTokenForStudent(
                    expectedMap.get("sub").toString(),
                    expectedMap.get("name").toString(),
                    Set.of(new SimpleGrantedAuthority(((Map) ((ArrayList) expectedMap.get("authorities")).get(0)).get("authority").toString())),
                    Role.STUDENT.toString(),
                    convertToLong(expectedMap.get("userId")),
                    convertToLong(expectedMap.get("studentId"))
            );

            // Return the new JWT token wrapped in JwtResponse
            return new JwtResponse(token);
        }catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to retrieve student courses due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }


    /**
     * Keeps the user session active by refreshing the JWT token if it is close to expiration.
     *
     * @param jwt The JWT token to be checked and potentially refreshed
     * @return JwtResponse containing the new JWT token if refreshed, otherwise throws an exception
     * @throws AbstractTokenExpiredException if the JWT token is expired
     * @throws AbstractStudentException for other exceptions during token processing
     */
    public JwtResponse keepUserSession10Minutes(String jwt) throws Exception {
        try {
            // Decrypt and parse the JWT token
            Map<String, Object> expectedMap;
            try {
                jwt = decrypt(jwt);
                    expectedMap = jwtUtils.parseToken(jwt);
            } catch (Exception e) {
                throw new RuntimeException("Invalid Token!");
            }

            // Extract user ID and expiration time from the token
            String userIdString = expectedMap.get("userId").toString();
            String exp = expectedMap.get("exp").toString();
            double userExpDouble = Double.parseDouble(exp);

            // Get current time in seconds (Unix timestamp)
            long currentTimeInSeconds = System.currentTimeMillis() / 1000;
            double currentTimeDouble = (double) currentTimeInSeconds;

            // Calculate the difference in minutes between the current time and token expiration time
            double differenceInMinutes = (currentTimeDouble - userExpDouble) / 60;

            if(differenceInMinutes <0)
                return null;
            // Check if the token is about to expire within 5 minutes
            if (differenceInMinutes < 5 && differenceInMinutes>0) {

                // Convert user ID to long
                double userIdDouble = Double.parseDouble(userIdString);
                long userId = (long) userIdDouble;

                // Find the user by ID
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                if (user == null) {
                    throw new RuntimeException("Invalid user");
                }

                // Generate a new JWT token with refreshed information
                String token = jwtUtils.doGenerateRefreshTokenForStudent(
                        expectedMap.get("sub").toString(),
                        expectedMap.get("name").toString(),
                        Set.of(new SimpleGrantedAuthority(((Map) ((ArrayList) expectedMap.get("authorities")).get(0)).get("authority").toString())),
                        Role.STUDENT.toString(),
                        convertToLong(expectedMap.get("userId")),
                        convertToLong(expectedMap.get("studentId"))
                );

                return new JwtResponse(token);
            } else {
                throw new RuntimeException("Token expired");
            }
        } catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to retrieve student courses due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }



    public User loadUserByEmail(String email){
        User userDetails = loadUserByUsername(email);
        return  (User) userDetails;
    }

    public User loadUserByUsername(String email) {
        try {

            if (isValidEmail(email))
                return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new RuntimeException(messagesUtil.getMessage("user.not.found.msg")));

            throw new RuntimeException(messagesUtil.getMessage("user.not.found.msg"));
        }
        catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to retrieve student courses due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }

    public StudentLoginInformation getStudentLoginDetails(Long userId){
        try {
            Student student = studentRepository.findByUserId(userId);
            return StudentLoginInformation.builder()
                    .email(student.getEmail())
                    .id(student.getId())
                    .firstName(student.getFirstName())
                    .lastName(student.getLastName())
                    .role(student.getRole())
                    .build();

        } catch (ExpiredJwtException e) {
            // Handle the case where the JWT token is expired
            throw new AbstractTokenExpiredException("Token is expired");
        } catch (Exception e) {
            // Log the error message and throw a custom exception for other issues
            log.error("Unable to retrieve student courses due to: [ " + e.getMessage() + " ]");
            throw new AbstractStudentException(e.getMessage());
        }
    }





}
