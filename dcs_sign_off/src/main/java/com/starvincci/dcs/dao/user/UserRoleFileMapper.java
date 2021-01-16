package com.starvincci.dcs.dao.user;

import com.starvincci.dcs.pojo.user.UserRoleFile;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleFileMapper {

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

    /**
     * 根据文件id查询文件可操作范围
     * @param fileId
     * @return
     */
    List<UserRoleFile> getUserRoleFileByFileId(Integer fileId);

    /**
     * 删除一条权限
     * @param id
     * @return
     */
    int delUserRoleFile(Integer id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    UserRoleFile getOnly(Integer id);
}
