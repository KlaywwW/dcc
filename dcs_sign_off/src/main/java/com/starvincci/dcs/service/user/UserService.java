package com.starvincci.dcs.service.user;

import com.starvincci.dcs.pojo.user.Department;
import com.starvincci.dcs.pojo.Directory;
import com.starvincci.dcs.pojo.user.Users;

import java.util.List;

public interface UserService {

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    int addUser(Users user);

    /**
     * 修改用户状态
     *
     * @param userId 用户id
     */
    int updateUserStatus(int userId);

    /**
     * 查询用户
     * 连接部门表
     */
    List<Users> getAllUsers();

    /**
     * 修改用户信息
     *
     * @param users 用户
     * @return
     */
    int updateUsers(Users users);

    /**
     * 获取部门信息
     *
     * @return
     */
    List<Department> getAllDep();
    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    Users getUserById(Integer userId);
    Department getDepById(Integer departmentId);

    Directory getDirById(Integer dirId);
    Users getUserTop();
    Users findByAccount(Users users);
}
