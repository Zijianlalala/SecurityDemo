package com.example.SecurityDemo.service;

import com.example.SecurityDemo.model.Role;

public interface IRoleService {
    Role findByName(String name);
}
