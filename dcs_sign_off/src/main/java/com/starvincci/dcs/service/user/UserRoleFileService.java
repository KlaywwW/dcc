package com.starvincci.dcs.service.user;

import com.starvincci.dcs.pojo.user.UserRoleFile;

import java.util.List;

public interface UserRoleFileService {
    /**
     * 根据用户id查询文件可操作范围
     * @param userId 用户id
     * @return
     */
    List<UserRoleFile> getAllByUserId(Integer userId);
    /**
     * 判断是否存在
     * @param userRoleFile
     * @return
     */
    UserRoleFile getUserRoleFileOnly(UserRoleFile userRoleFile);
    /**
     * 给文件添加权限
     * @param userRoleFile
     * @return
     */
    int addUserRoleFile(UserRoleFile userRoleFile);
    int delUserRoleFile(Integer id);
    UserRoleFile getOnly(Integer id);
}
