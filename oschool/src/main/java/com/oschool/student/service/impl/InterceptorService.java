/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.service.impl;

import com.oschool.student.dto.common.JwtResponse;
import com.oschool.student.service.IUserService;
import com.oschool.student.util.MutableHttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class InterceptorService extends OncePerRequestFilter {
    private final IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Check if the request is for Swagger UI endpoints; if so, bypass the filter
        if (isSwaggerEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the Authorization header from the request
        String header = request.getHeader("Authorization");
        String refreshToken = request.getHeader("refreshToken");



        // Check if the header is for registration; if so, bypass the filter
        if (header != null && !header.isEmpty() && (header.equals("registration") || (refreshToken!=null && !refreshToken.isEmpty()))) {
            filterChain.doFilter(request, response);
            return;
        }

        // If the header is present, process it further
        if (header != null) {
            try {
                // Create a mutable request to allow header modifications
                MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);

                // Refresh the user session and obtain a new JWT token
                JwtResponse jwtResponse = userService.keepUserSession10Minutes(header);

                // If the JWT response is null, proceed without modification
                if (jwtResponse == null) {
                    filterChain.doFilter(mutableRequest, response);
                    return;  // Exit after processing the request
                }

                // Retrieve the new token from the JWT response
                String newToken = jwtResponse.getToken();

                // Set the new token in the response header
                response.setHeader("Authorization", newToken);

                // Update the request with the new token
                mutableRequest.putHeader("Authorization", newToken);

                // Continue with the modified request
                filterChain.doFilter(mutableRequest, response);
                return;  // Exit after processing the request

            } catch (Exception e) {
                // Handle any exceptions by sending an unauthorized error response
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;  // Exit after sending the error response
            }
        } else {
            // If the header is missing, send an unauthorized error response
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            return;  // Exit after sending the error response
        }
    }


    private boolean isSwaggerEndpoint(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("/swagger-ui.html") ||
                requestURI.contains("/webjars/springfox-swagger-ui") ||
                requestURI.contains("/swagger-resources") ||
                requestURI.contains("/v2/api-docs");
    }
}
