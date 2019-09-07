package com.example.SecurityDemo.model;

import com.example.SecurityDemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;



    @GetMapping("/list")
    @PreAuthorize("hasPermission('user','read') or hasRole('ROLE_ADMINISTRATOR')")
    public List<?> list() {
        return userService.list();
    }
}
