package com.starvincci.dcs.service.user;

import com.starvincci.dcs.dao.user.RoleMapper;

import com.starvincci.dcs.pojo.user.RoleGroup;
import com.starvincci.dcs.pojo.user.Roles;
import com.starvincci.dcs.pojo.user.UserRoleGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements  RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Roles> getRoleByGroupId(Integer groupId) {
        return roleMapper.getRoleByGroupId(groupId);
    }

    @Override
    public List<RoleGroup> getAllRoleGroup() {
        return roleMapper.getAllRoleGroup();
    }

    @Override
    public int addRoleGroup(RoleGroup roleGroup) {
        return roleMapper.addRoleGroup(roleGroup);
    }

    @Override
    public int updateGroupName(RoleGroup roleGroup) {
        return roleMapper.updateGroupName(roleGroup);
    }

    @Override
    public List<Roles> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    @Override
    public int addRolesToGroup(Integer groupId, Integer roleId) {
        return roleMapper.addRolesToGroup(groupId,roleId);
    }

    @Override
    public UserRoleGroup getSame(Integer groupId, Integer roleId) {
        return roleMapper.getSame(groupId,roleId);
    }

    @Override
    public int delRole(Integer groupId, Integer roleId) {
        return roleMapper.delRole(groupId,roleId);
    }
}
