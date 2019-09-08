package com.example.SecurityDemo.service;

import com.example.SecurityDemo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {
    User findByUsername(String username);
    List<User> list();
    User save(User user);
}
