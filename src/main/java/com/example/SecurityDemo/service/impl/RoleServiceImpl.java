package com.example.SecurityDemo.service.impl;

import com.example.SecurityDemo.model.Role;
import com.example.SecurityDemo.repository.RoleRepository;
import com.example.SecurityDemo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
