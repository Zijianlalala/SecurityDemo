package com.example.SecurityDemo.model;

import lombok.Data;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;
import java.util.Map;

@Data
public class JsonPermissions {

    private List<SimplePermission> permissons;

    @Data
    public static class SimplePermission {
        /**
         * 资源id
         */
        private String resourceId;
        /**
         * 资源名
         */
        private String resourceName;
        /**
         * 权限列表
         */
        private Map<String, String> privileges;
        /**
         * 是否被遗弃
         */
        private boolean abandon = false;
    }
}
