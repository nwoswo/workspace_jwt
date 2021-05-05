package com.nwo.demo.security.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public enum ApplicationUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;



    ApplicationUserPermission(String s) {
        this.permission = s;
    }

    public String getPermission() {
        return permission;
    }


}
