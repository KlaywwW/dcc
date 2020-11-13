package com.starvincci.dcs.controller;

import com.starvincci.dcs.pojo.user.UserRole;
import com.starvincci.dcs.pojo.user.UserRoleFile;
import com.starvincci.dcs.service.files.FilesServiceImpl;
import com.starvincci.dcs.service.user.UserRoleFileServiceImpl;
import com.starvincci.dcs.service.user.UserRoleServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class UserRoleFileController {
    @Resource
    private UserRoleServiceImpl userRoleService;
    @Resource
    private UserRoleFileServiceImpl userRoleFileService;
    @Resource
    private FilesServiceImpl filesService;


    /**
     * 1. 获取用户id
     * 2. 根据用户id查 角色
     * 3. 根据角色的主键id查找 文件权限
     *
     * @return
     */
    @GetMapping("/getFileAuth")
    public List<UserRole> getFileAuth(@RequestParam("userId") Integer userId) {

        List<UserRole> userRoleList=userRoleService.getAllUserRole(userId);
        for (UserRole userRole: userRoleList) {
            List<UserRoleFile> userRoleFileList=userRoleFileService.getAllByUserRoleId(userRole.getId());
            for (UserRoleFile userRoleFile:userRoleFileList) {
                userRoleFile.setFiles(filesService.getFileByFileId(userRoleFile.getFileId()));
                userRoleFile.setUserRole(userRoleService.getUserRoleById(userRoleFile.getUserRoleId()));
            }

            userRole.setUserRoleFileList(userRoleFileList);
        }

        return userRoleList;
    }
}
