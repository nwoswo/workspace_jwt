package com.nwo.demo.security.config;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.nwo.demo.security.config.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            COURSE_READ,
            COURSE_WRITE,
            STUDENT_READ,
            STUDENT_WRITE
    )),
    ADMINTRAINEE(Sets.newHashSet(
          COURSE_READ,
          STUDENT_READ
          ));


    private final Set<ApplicationUserPermission> permissions;


    ApplicationUserRole(HashSet<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    //solo para mirar los permisos
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
           Set<SimpleGrantedAuthority> permissons =  getPermissions().stream()
                .map(permission ->  new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

           permissons.add((new SimpleGrantedAuthority("ROLE_"+this.name())));

           return permissons;
    }
}
