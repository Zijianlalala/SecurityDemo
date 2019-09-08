package com.example.SecurityDemo.config;

import com.example.SecurityDemo.model.Role;
import com.example.SecurityDemo.model.User;
import com.example.SecurityDemo.repository.RoleRepository;
import com.example.SecurityDemo.repository.UserRepository;
import com.example.SecurityDemo.util.CommonGsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 系统初始化类，主要用于加载内置数据到数据库上
 */
@Component
public class SystemInitializer {

    @Value("${initialization.file.users:users.json}")
    private String userFileName;
    @Value("${initialization.file.roles:roles.json}")
    private String roleFileName;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public boolean initialize() throws Exception {
        try {
            InputStream userInputStream =
                    getClass().getClassLoader().getResourceAsStream(userFileName);
            if (userInputStream == null) {
                throw new Exception("initialization user file not found: " + userFileName);
            }
            InputStream roleInputStream =
                    getClass().getClassLoader().getResourceAsStream(roleFileName);
            if (roleInputStream == null) {
                throw new Exception("initialization user file not found: " + roleFileName);
            }
            //导入初试的系统管理员角色
            //使用TypeToken，可以在类对象进行序列化时保存它的泛型参数
            Type roleTokenType = new TypeToken<ArrayList<Role.RoleJson>>(){}.getType();
            ArrayList<Role.RoleJson> roleJsons = CommonGsonBuilder.create().fromJson(new InputStreamReader(roleInputStream, StandardCharsets.UTF_8), roleTokenType);
            for (Role.RoleJson roleJson : roleJsons) {
                Role role = new Role();
                role.wrapRole(roleJson);
                if (roleRepository.findByName(role.getName()) == null) {
                    roleRepository.save(role);
                }
            }
            //导入初始的系统管理员
            Type userTokenType = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> users = CommonGsonBuilder.create().fromJson(new InputStreamReader(userInputStream, StandardCharsets.UTF_8), userTokenType);
            for (User user : users) {
                if (userRepository.findByUsername(user.getUsername()) == null) {
                    userRepository.save(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
