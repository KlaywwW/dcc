package com.starvincci.dcs.config.security;

import com.starvincci.dcs.dao.user.UserMapper;
import com.starvincci.dcs.pojo.user.UserRole;
import com.starvincci.dcs.pojo.user.UserRoleFile;
import com.starvincci.dcs.pojo.user.Users;
import com.starvincci.dcs.service.files.FilesService;
import com.starvincci.dcs.service.user.UserRoleFileService;
import com.starvincci.dcs.service.user.UserRoleServiceImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 登陆验证时，通过username获取用户的所有权限信息
 * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleServiceImpl userRoleService;
    @Resource
    private UserRoleFileService userRoleFileService;
    @Resource
    private FilesService filesService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println("s-----"+s);
        Users users = new Users();
        users.setAccount(s);
//        users.setPassword("123");

        Users reulstUser = userMapper.findByAccount(users);
        if (reulstUser == null) {
            throw new UsernameNotFoundException("Username :" + s + "not found");
        }
//        List<UserRole> roles = userRoleService.getAllUserRole(reulstUser.getId());
        List<UserRole> userRoleList=userRoleService.getAllUserRole(reulstUser.getId());
        for (UserRole userRole: userRoleList) {
            List<UserRoleFile> userRoleFileList=userRoleFileService.getAllByUserRoleId(userRole.getId());
            for (UserRoleFile userRoleFile:userRoleFileList) {
                userRoleFile.setFiles(filesService.getFileByFileId(userRoleFile.getFileId()));
                userRoleFile.setUserRole(userRoleService.getUserRoleById(userRoleFile.getUserRoleId()));
            }
            userRole.setUserRoleFileList(userRoleFileList);
        }


        Collection<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
        for (UserRole role : userRoleList) {
            authorities.add(new SimpleGrantedAuthority(role.getRoles().getRoleCode()));
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("user", reulstUser);
        map.put("role", userRoleList);

        MyUserDetails securityUser = new MyUserDetails();
        securityUser.setUsername(reulstUser.getAccount());
        securityUser.setPassword(reulstUser.getPassword());

        if (reulstUser.getIsStatus() == 1) {
            securityUser.setEnabled(false);
        }else{
            securityUser.setEnabled(true);
        }

        securityUser.setObj(map);
        securityUser.setAuthorities(authorities);
        return securityUser;
    }
}
