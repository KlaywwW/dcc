package com.starvincci.dcs.dao.user;

import com.starvincci.dcs.pojo.user.Department;
import com.starvincci.dcs.pojo.Directory;
import com.starvincci.dcs.pojo.user.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(Users user);

    /**
     * 修改用户状态
     * @param userId 用户id
     */
    int updateUserStatus(int userId);

    /**
     * 查询用户
     * 连接部门表
     * 连接角色
     */
    List<Users> getAllUsers();

    /**
     * 修改用户信息
     * @param users 用户
     * @return
     */
    int updateUsers(Users users);

    /**
     * 登录
     * @param users 用户名
     * @return
     */
    Users findByAccount(Users users);

    /**
     * 获取部门信息
     * @return
     */
    List<Department> getAllDep();

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    Users getUserById(Integer userId);

    /**
     * 根据id查询部门
      * @param departmentId
     * @return
     */
    Department getDepById(Integer departmentId);

    /**
     * 根据id查询目录
     * @param dirId
     * @return
     */
    Directory getDirById(Integer dirId);

    /**
     * 查询最新添加的用户信息
     * @return
     */
    Users getUserTop();


}
