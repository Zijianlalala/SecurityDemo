package com.example.SecurityDemo.security;

import com.alibaba.fastjson.JSONArray;
import com.example.SecurityDemo.model.JsonPermissions;
import com.example.SecurityDemo.model.Role;
import com.example.SecurityDemo.model.User;
import com.example.SecurityDemo.service.IRoleService;
import com.example.SecurityDemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username: %s", username));
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = Arrays.asList(user.getRoles().split(","));
        for (String roleName : roles) {
            Role role = roleService.findByName(roleName);
            if (role == null) {
                continue;
            }
            List<JsonPermissions.SimplePermission> permissions = JSONArray.parseArray(role.getPermissions(), JsonPermissions.SimplePermission.class);
            for (JsonPermissions.SimplePermission permission : permissions) {
                for (String privilege : permission.getPrivileges().keySet()) {
                    authorities.add(new SimpleGrantedAuthority(String.format("%s-%s", permission.getResourceId(), privilege)));
                }
            }
        }
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), authorities);
    }
}
