package com.example.SecurityDemo.controller;

import com.example.SecurityDemo.model.User;
import com.example.SecurityDemo.service.IUserService;
import com.example.SecurityDemo.util.RestResponse;
import com.example.SecurityDemo.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @param body
     * @return
     */
    @PostMapping("/login")
    public RestResponse<?> login(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String password = body.get("password");
            User user = userService.findByUsername(username);
            if (user == null) {
                return RestResponse.bad(-1, "用户名不存在，请重新登录");
            }
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            token.setDetails(user);
            final Map<String, UserDetailsService> userDetailsServices
                    = SpringContextUtil.getApplicationContext().getBeansOfType(UserDetailsService.class);
            UserDetailsService userDetailsService = (UserDetailsService) userDetailsServices.values().toArray()[0];

            DaoAuthenticationProvider authenticator = new DaoAuthenticationProvider();
            authenticator.setUserDetailsService(userDetailsService);
            authenticator.setPasswordEncoder(User.PASSWORD_ENCODER);
            Authentication authentication = authenticator.authenticate(token);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            return RestResponse.good(user);

        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.bad(-2, "登录失败，请检查账号和密码是否正确");
        }
    }

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasPermission('user','read') or hasRole('ROLE_ADMINISTRATOR')")
    public RestResponse<?> list() {
        List<User> users = userService.list();
        return RestResponse.good(users);
    }

    /**
     * 用户注册
     * @param body
     * @return
     */
    @PostMapping("/register")
    public RestResponse<?> register(@RequestBody Map<String, String> body) {
        User user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(body.get("password"));
        user.setRealName(body.get("realName"));
        user.setRoles(body.get("roles"));
        user.setType(body.get("type"));
        user = userService.save(user);
        return RestResponse.good(user);
    }

}
