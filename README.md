# SecurityDemo

## 系统实现步骤
1. 先定义权限集合`permissions.json`
2. 创建于权限相关的实体类`JsonPermissions`
3. 创建角色实体类`Role`
4. 创建用户实体类`User`
5. 创建系统初始角色`roles.json`和初始管理员`user.json`
6. 加载初始化类`SystemInitializer`
7. 实现`UserDetailsService`
8. 后端接口设置访问权限`UserController`
9. 实现`PermissonEvalutor`

## 问题
1. 初始管理员密码不正确，需要写一个注册接口，创建新用户
2. 自动生成的`role`表中`permissions`字段不够用，需要增加长度