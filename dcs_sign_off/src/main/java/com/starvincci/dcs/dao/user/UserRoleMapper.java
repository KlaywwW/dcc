package com.starvincci.dcs.dao.user;

import com.starvincci.dcs.pojo.user.Roles;
import com.starvincci.dcs.pojo.user.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    /**
     * 添加用户的一个角色
     * @param userRole
     * @return
     */
    int addUserRole(UserRole userRole);

    /**
     * 根据用户id获取用户角色
     * @param userId 用户id
     * @return
     */
    List<UserRole> getAllUserRole(Integer userId);

    /**
     * 修改用户角色
     * @param userRole
     * @return
     */
    int updateUserRole(UserRole userRole);

    /**
     * 获取所有角色
     * @return
     */
    List<Roles> getAllRoles();

    /**
     * 根据主键id 查找
     * @param id
     * @return
     */
    UserRole getUserRoleById(Integer id);





}
