package com.starvincci.dcs.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starvincci.dcs.pojo.user.RoleGroup;
import com.starvincci.dcs.pojo.user.Roles;
import com.starvincci.dcs.pojo.user.UserRoleGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.management.relation.Role;
import java.util.List;

@Mapper
public interface RoleMapper{

    /**
     * 添加用户的一个角色
     * @param userRole
     * @return
     */
//    int addUserRole(UserRole userRole);

    /**
     * 根据权限组id获取用户权限
     *
     * @param groupId 权限组id
     * @return
     */
    List<Roles> getRoleByGroupId(Integer groupId);

    /**
     * 获取所有权限组
     * @return
     */
    List<RoleGroup> getAllRoleGroup();

    /**
     * 添加用户组
     * @param roleGroup
     * @return
     */
    int addRoleGroup(RoleGroup roleGroup);

    /**
     * 修改用户组
     * @param roleGroup
     * @return
     */
    int updateGroupName(RoleGroup roleGroup);

    /**
     * 获取所有权限
     * @return
     */
    List<Roles> getAllRoles();


    /**
     * 添加权限进用户组
     * @param groupId 组别id
     * @param roleId  权限id
     * @return
     */
    int addRolesToGroup(@Param("groupId") Integer groupId,@Param("roleId") Integer roleId);

    /**
     * 查询在同一用户组中是否有相同的权限
     * @param groupId
     * @param roleId
     * @return
     */
    UserRoleGroup getSame(@Param("groupId") Integer groupId,@Param("roleId") Integer roleId);
    /**
     * 从用户组中删除一条权限
     * @param groupId
     * @param roleId
     * @return
     */
    int delRole(@Param("groupId") Integer groupId, @Param("roleId") Integer roleId);





}
