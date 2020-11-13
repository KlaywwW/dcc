package com.starvincci.dcs.service.user;

import com.starvincci.dcs.dao.user.UserRoleMapper;
import com.starvincci.dcs.pojo.user.Roles;
import com.starvincci.dcs.pojo.user.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;


    @Override
    public int addUserRole(UserRole userRole) {
        return userRoleMapper.addUserRole(userRole);
    }

    @Override
    public List<UserRole> getAllUserRole(Integer userId) {
        return userRoleMapper.getAllUserRole(userId);
    }

    @Override
    public int updateUserRole(UserRole userRole) {
        return userRoleMapper.updateUserRole(userRole);
    }

    @Override
    public List<Roles> getAllRoles() {
        return userRoleMapper.getAllRoles();
    }

    @Override
    public UserRole getUserRoleById(Integer id) {
        return userRoleMapper.getUserRoleById(id);
    }
}
