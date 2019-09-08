package com.example.SecurityDemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Configuration
public class MyPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        boolean access = false;
        if (authentication.getPrincipal().toString().compareToIgnoreCase("anonymousUser") != 0) {
            String privilege = targetDomainObject + "-" + permission;
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (privilege.equalsIgnoreCase(authority.getAuthority())) {
                    access = true;
                    break;
                }
            }
         }
        return access;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
