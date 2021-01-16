package com.starvincci.dcs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.starvincci.dcs.pojo.RespBean;
import com.starvincci.dcs.pojo.user.RoleGroup;
import com.starvincci.dcs.pojo.user.UserRoleFile;
import com.starvincci.dcs.pojo.user.UserRoleGroup;
import com.starvincci.dcs.service.files.FilesServiceImpl;
import com.starvincci.dcs.service.user.RoleServiceImpl;
import com.starvincci.dcs.service.user.UserRoleFileServiceImpl;
//import com.starvincci.dcs.service.user.UserRoleServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限控制器
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleServiceImpl roleService;

    /**
     * 获取所有权限组
     *
     * @return
     */
    @GetMapping("/getRoleGroup")
    public RespBean getRoleGroup() {
        return RespBean.ok("ok", roleService.getAllRoleGroup());
    }

    /**
     * 根据权限组id查询角色
     *
     * @param groupId
     * @return
     */
    @GetMapping("/getRoleByGroup")
    public RespBean getRoleByGroup(Integer groupId) {
        return RespBean.ok("ok", roleService.getRoleByGroupId(groupId));
    }

    @PostMapping("/addRoleGroup")
    public RespBean addRoleGroup(@RequestBody String strs) {
        JSONObject jsonObject = JSON.parseObject(strs);
        RoleGroup roleGroup = new RoleGroup();
        roleGroup.setGroupName(jsonObject.getString("groupName"));
        int result = roleService.addRoleGroup(roleGroup);
        if (result == 1) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PostMapping("/updateGroupName")
    public RespBean updateGroupName(@RequestBody String strs) {
        JSONObject jsonObject = JSON.parseObject(strs);
        RoleGroup roleGroup = new RoleGroup();
        roleGroup.setGroupId(jsonObject.getInteger("groupId"));
        roleGroup.setGroupName(jsonObject.getString("groupName"));
        int result = roleService.updateGroupName(roleGroup);
        if (result == 1) {
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @GetMapping("/getAllRoles")
    public RespBean getAllRoles() {
        return RespBean.ok("ok",roleService.getAllRoles());
    }

    @PostMapping("/addRolesToGroup")
    public RespBean addRolesToGroup(@RequestBody String strs) {
        JSONObject jsonObject = JSON.parseObject(strs);
        Integer groupId=jsonObject.getInteger("groupId");
        Integer roleId=jsonObject.getInteger("roleId");
        UserRoleGroup userRoleGroup=roleService.getSame(groupId,roleId);
        if (userRoleGroup==null){
            int result=roleService.addRolesToGroup(groupId,roleId);
            if (result==1){
                return RespBean.ok("添加成功");
            }
        }else{
            return RespBean.ok("已存在");
        }
        return RespBean.ok("添加失败");
    }

    @GetMapping("/delRole")
    public RespBean delRole(@RequestParam("groupId") Integer groupId,@RequestParam("roleId") Integer roleId){
        int result=roleService.delRole(groupId,roleId);
        if (result==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.ok("删除失败");
    }

}
