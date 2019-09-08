package com.example.SecurityDemo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    /**
     * 创建时间
     */
    private Long createTime = System.currentTimeMillis();
    /**
     * 是否被移除
     */
    private Boolean isRemoved = false;
    /**
     * 角色名，用于验证
     */
    private String name;
    /**
     * 用户中文名，用于显示
     */
    private String nickname;
    /**
     * 角色描述信息
     */
    private String description;
    /**
     * 是否为内置
     */
    private Boolean builtIn = false;
    /**
     * 是否被禁用
     */
    private Boolean banned = false;
    /**
     * 角色可进行的操作列表
     */
    private String permissions;
    /**
     * 角色创建者
     */
    private String proposer;

    public void setName(String name) {
        if (name.indexOf("ROLE_") == -1) {
            this.name = "ROLE_" + name;
        } else {
            this.name = name;
        }
    }

    public void wrapRole(RoleJson roleJson) throws JsonProcessingException {
        this.name = roleJson.getName();
        this.nickname = roleJson.getNickname();
        this.description = roleJson.getDescription();
        this.builtIn = roleJson.getBuiltIn();
        this.banned = roleJson.getBanned();
        this.proposer = roleJson.getProposer();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(roleJson.getPermissions());
        this.permissions = jsonString;
    }


    @Data
    @NoArgsConstructor
    public class RoleJson {
        /**
         * 角色名，用于验证
         */
        private String name;
        /**
         * 用户中文名，用于显示
         */
        private String nickname;
        /**
         * 角色描述信息
         */
        private String description;
        /**
         * 是否为内置
         */
        private Boolean builtIn = false;
        /**
         * 是否被禁用
         */
        private Boolean banned = false;
        /**
         * 角色可进行的操作列表
         */
        private List<JsonPermissions.SimplePermission> permissions;
        /**
         * 角色创建者
         */
        private String proposer;
    }
}


