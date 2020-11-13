package com.starvincci.dcs.dao.user;

import com.starvincci.dcs.pojo.user.UserRoleFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleFileMapper {

    /**
     * 获取文件权限
     * @param userRoleId
     * @return
     */
    List<UserRoleFile> getAllByUserRoleId(Integer userRoleId);

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

    List<UserRoleFile> getUserRoleFileByFileId(Integer fileId);
}
