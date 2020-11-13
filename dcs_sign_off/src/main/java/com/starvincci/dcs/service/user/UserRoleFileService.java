package com.starvincci.dcs.service.user;

import com.starvincci.dcs.pojo.user.UserRoleFile;

import java.util.List;

public interface UserRoleFileService {
    List<UserRoleFile> getAllByUserRoleId(Integer userRoleId);

    UserRoleFile getUserRoleFileOnly(UserRoleFile userRoleFile);

    int addUserRoleFile(UserRoleFile userRoleFile);
    List<UserRoleFile> getUserRoleFileByFileId(Integer fileId);
}
