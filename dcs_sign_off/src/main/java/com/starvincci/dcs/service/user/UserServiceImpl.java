package com.starvincci.dcs.service.user;

import com.starvincci.dcs.dao.user.UserMapper;
import com.starvincci.dcs.pojo.user.Department;
import com.starvincci.dcs.pojo.Directory;
import com.starvincci.dcs.pojo.user.Users;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public int addUser(Users user) {
        return userMapper.addUser(user);
    }

    @Override
    public int updateUserStatus(int userId) {
        return userMapper.updateUserStatus(userId);
    }

    @Override
    public List<Users> getAllUsers() {



        return userMapper.getAllUsers();
    }

    @Override
    public int updateUsers(Users users) {
        return userMapper.updateUsers(users);
    }

    @Override
    public List<Department> getAllDep() {
        return userMapper.getAllDep();
    }

    @Override
    public Users getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public Department getDepById(Integer departmentId) {
        return userMapper.getDepById(departmentId);
    }

    @Override
    public Directory getDirById(Integer dirId) {
        return userMapper.getDirById(dirId);
    }

    @Override
    public Users getUserTop() {
        return userMapper.getUserTop();
    }

    @Override
    public Users findByAccount(Users users) {
        return userMapper.findByAccount(users);
    }
}
