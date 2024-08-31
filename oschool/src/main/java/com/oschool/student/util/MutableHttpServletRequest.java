/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class MutableHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public void putHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        if (customHeaders.containsKey(name)) {
            return customHeaders.get(name);
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        // Combine custom headers and original headers
        Set<String> set = new HashSet<>(customHeaders.keySet());
        Enumeration<String> e = super.getHeaderNames();
        while (e.hasMoreElements()) {
            set.add(e.nextElement());
        }
        return Collections.enumeration(set);
    }


    @Override
    public Enumeration<String> getHeaders(String name) {
        if (customHeaders.containsKey(name)) {
            return Collections.enumeration(Collections.singletonList(customHeaders.get(name)));
        }
        return super.getHeaders(name);
    }

    public void replaceHeader(String name, String value) {
        customHeaders.put(name, value);
    }
}

