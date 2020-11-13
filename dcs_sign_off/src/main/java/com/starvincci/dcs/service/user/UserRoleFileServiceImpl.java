package com.starvincci.dcs.service.user;

import com.starvincci.dcs.dao.user.UserRoleFileMapper;
import com.starvincci.dcs.pojo.user.UserRoleFile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleFileServiceImpl implements UserRoleFileService {


    @Resource
    private UserRoleFileMapper userRoleFileMapper;


    @Override
    public List<UserRoleFile> getAllByUserRoleId(Integer userRoleId) {
        return userRoleFileMapper.getAllByUserRoleId(userRoleId);
    }

    @Override
    public UserRoleFile getUserRoleFileOnly(UserRoleFile userRoleFile) {
        return userRoleFileMapper.getUserRoleFileOnly(userRoleFile);
    }

    @Override
    public int addUserRoleFile(UserRoleFile userRoleFile) {
        return userRoleFileMapper.addUserRoleFile(userRoleFile);
    }

    @Override
    public List<UserRoleFile> getUserRoleFileByFileId(Integer fileId) {
        return userRoleFileMapper.getUserRoleFileByFileId(fileId);
    }
}
