package com.starvincci.dcs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starvincci.dcs.pojo.*;
import com.starvincci.dcs.pojo.user.Department;
import com.starvincci.dcs.pojo.user.Roles;
import com.starvincci.dcs.pojo.user.UserRole;
import com.starvincci.dcs.pojo.user.Users;
import com.starvincci.dcs.service.user.UserRoleServiceImpl;
import com.starvincci.dcs.service.user.UserService;
import com.starvincci.dcs.service.user.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserRoleServiceImpl userRoleService;
    @Resource
    private UserServiceImpl userService;

    @GetMapping("/show")
    public RespBean login(HttpServletRequest request) {

        List<Users> usersList = userService.getAllUsers();
        List<Users> usersAndRoles = new ArrayList<>();
        for (Users user : usersList) {
            user.setUserRoleList(userRoleService.getAllUserRole(user.getId()));
            usersAndRoles.add(user);
        }
        return RespBean.ok("ok", usersAndRoles);
    }

    @GetMapping("/department")
    public RespBean getDepartment(HttpServletRequest request) {

        List<Department> departmentList = userService.getAllDep();

        return RespBean.ok("ok", departmentList);
    }

    @GetMapping("/roles")
    public RespBean getRoles(HttpServletRequest request) {

        List<Roles> rolesList = userRoleService.getAllRoles();

        return RespBean.ok("ok", rolesList);
    }

    @PutMapping("/updateUser/{id}")
    public RespBean updateUser(@PathVariable Integer id, @RequestBody String data) {
        System.out.println(data);
        JSONObject jsonObject = JSON.parseObject(data);
        // 修改用户
        Users user = new Users();
        user.setId(jsonObject.getInteger("id"));
        user.setAccount(jsonObject.getString("account"));
        user.setPassword(jsonObject.getString("password"));
        user.setAge(0);
        user.setUsername(jsonObject.getString("username"));
        user.setDirId(jsonObject.getInteger("dirId"));
        if (jsonObject.getString("delivery") == "true") {
            user.setIsStatus(1);
        } else {
            user.setIsStatus(0);
        }
        user.setDepartmentId(jsonObject.getInteger("dep"));
        int resUser = userService.updateUsers(user);

        if (resUser == 1) {

            return RespBean.ok("ok");
        }
        return RespBean.error("error");
    }

    @PostMapping("/addUser")
    private String addUser(@RequestBody String strs) {
        JSONObject jsonObject = JSON.parseObject(strs);
        System.out.println(strs);

        Users users = new Users();
        users.setAccount(jsonObject.getString("account"));
        users.setUsername(jsonObject.getString("username"));
        users.setPassword(jsonObject.getString("password"));
        users.setDirId(jsonObject.getInteger("dirId"));
        users.setAge(0);
        users.setDepartmentId(jsonObject.getInteger("dep"));
        System.err.println(users.toString());
        Users resUser = userService.findByAccount(users);
        if (resUser != null) {
            return "添加失败，存在相同账号名";
        }
        int res = userService.addUser(users);
        if (res == 1) {

            Users newUsers = userService.getUserTop();
            UserRole userRole1 = new UserRole();
            userRole1.setUserId(newUsers.getId());
            userRole1.setRoleId(3);
            int roleRes1 = userRoleService.addUserRole(userRole1);
            UserRole userRole2 = new UserRole();
            userRole2.setUserId(newUsers.getId());
            userRole2.setRoleId(4);
            int roleRes2 = userRoleService.addUserRole(userRole2);
            if (roleRes1 > 0 && roleRes2 > 0) {
                return "添加成功";
            }

        }

        return "添加失败";
    }

}
