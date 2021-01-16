package com.starvincci.dcs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starvincci.dcs.dao.menu.MenuMapper;
import com.starvincci.dcs.dao.user.RoleMapper;
import com.starvincci.dcs.pojo.*;
import com.starvincci.dcs.pojo.menu.UserMenu;
import com.starvincci.dcs.pojo.user.Department;
import com.starvincci.dcs.pojo.user.Roles;
import com.starvincci.dcs.pojo.user.UserRoleFile;
import com.starvincci.dcs.pojo.user.Users;
import com.starvincci.dcs.service.menu.MenuServiceImpl;
import com.starvincci.dcs.service.user.RoleServiceImpl;
import com.starvincci.dcs.service.user.UserRoleFileService;
import com.starvincci.dcs.service.user.UserService;
import com.starvincci.dcs.service.user.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;
    @Resource
    private RoleServiceImpl roleService;
    @Resource
    private MenuServiceImpl menuService;
    @Resource
    private UserRoleFileService userRoleFileService;

    /**
     *
     *
     * @return
     */
    @GetMapping("/show")
    public RespBean show() {

        List<Users> usersList = userService.getAllUsers();
        List<Roles> roleByGroupId =null;
        List<UserMenu> userMenu =null;
        HashMap<String,Object> map=null;
        for (Users user : usersList) {
            roleByGroupId = roleService.getRoleByGroupId(user.getRoleGroupId());
            userMenu = menuService.getUserMenuByUid(user.getId());
            map=new HashMap<>();
            map.put("roles",roleByGroupId);
            map.put("menus",userMenu);
            user.setUserMap(map);
        }
        return RespBean.ok("ok", usersList);
    }

    @GetMapping("/department")
    public RespBean getDepartment(HttpServletRequest request) {
        List<Department> departmentList = userService.getAllDep();
        return RespBean.ok("ok", departmentList);
    }


    @PostMapping("/updateUser/{id}")
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
        user.setDepartmentId(jsonObject.getInteger("dep"));
        user.setRoleGroupId(jsonObject.getInteger("roleGroupId"));
        if (jsonObject.getString("delivery") == "true" || "true".equals(jsonObject.getString("delivery"))) {
            user.setIsStatus(1);
        } else {
            user.setIsStatus(0);
        }
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
        users.setDepartmentId(jsonObject.getInteger("dep"));
        //用户组默认为普通用户
        users.setRoleGroupId(jsonObject.getInteger("roleGroupId"));
        users.setAge(0);
        System.err.println(users.toString());

        Users resUser = userService.findByAccount(users);
        if (resUser != null) {
            return "添加失败，存在相同账号名";
        }
        int res = userService.addUser(users);
        if (res == 1) {

            Users newUsers = userService.getUserTop();
//            新用户默认进入首页
            int menuResult=menuService.addUserMenu(1,newUsers.getId());

            if (menuResult==1){
                return "添加成功";
            }
        }

        return "添加失败";
    }

    @GetMapping("/getAllMenu")
    public RespBean getAllMenu(){
        return RespBean.ok("ok",menuService.getAllMenus());
    }

    @PostMapping("/addUserMenu")
    public RespBean addUserMenu(@RequestBody String strs){
        JSONObject jsonObject = JSON.parseObject(strs);
        Integer mid=jsonObject.getInteger("mid");
        Integer uid=jsonObject.getInteger("uid");
        UserMenu userMenu=menuService.getSameUserMenu(mid,uid);
        if (userMenu==null){
            int result = menuService.addUserMenu(mid, uid);
            if (result==1){
                return RespBean.ok("添加成功");
            }
        }else{
            return RespBean.ok("已存在");
        }
        return RespBean.error("添加失败");
    }
    @GetMapping("/delUserMenu")
    public RespBean delUserMenu(@RequestParam("mid") Integer mid,@RequestParam("uid") Integer uid){
        int result=menuService.delUserMenu(mid,uid);
        if (result==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.ok("删除失败");
    }
    @GetMapping("/getUserMenuByUid")
    public RespBean getUserMenuByUid(@RequestParam("uid") Integer uid){
        return RespBean.ok("ok",menuService.getUserMenuByUid(uid));
    }

    /**
     * 待用  刷新用户权限
     * @param uid
     * @return
     */
    @GetMapping("/refreshUser")
    public RespBean refreshUser(@RequestParam("userId") Integer uid){
        Users user = userService.getUserById(uid);
        HashMap<String,Object> map=new HashMap<>();
        List<UserMenu> userMenus=menuService.getUserMenuByUid(user.getId());
        List<Roles> rolesList = roleService.getRoleByGroupId(user.getRoleGroupId());
        List<UserRoleFile> roleFile = userRoleFileService.getAllByUserId(user.getId());
        map.put("user", user);
        map.put("role", rolesList);
        map.put("menu",userMenus);
        map.put("roleFile",roleFile);
        return RespBean.ok("ok",map);
    }
    @PostMapping("/userCenterUpdate")
    public RespBean userCenterUpdate(@RequestBody String strs){
        JSONObject jsonObject =JSON.parseObject(strs);
        Integer userId=jsonObject.getInteger("id");
        String account = jsonObject.getString("account");
        String password = jsonObject.getString("password");
        Integer departmentId = jsonObject.getInteger("departmentId");
        String username = jsonObject.getString("username");
        Integer isStatus = jsonObject.getInteger("isStatus");
        Integer roleGroupId = jsonObject.getInteger("roleGroupId");
        Users user=new Users();
        user.setDepartmentId(departmentId);
        user.setAccount(account);
        user.setPassword(password);
        user.setUsername(username);
        user.setIsStatus(isStatus);
        user.setRoleGroupId(roleGroupId);
        user.setId(userId);
        int result=userService.updateUsers(user);
        if (result==1){
            return RespBean.ok("ok","修改成功");
        }

        return RespBean.error("error","修改失败");
    }


}
