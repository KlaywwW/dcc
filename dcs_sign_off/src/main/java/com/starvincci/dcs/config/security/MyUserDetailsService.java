package com.starvincci.dcs.config.security;

import com.starvincci.dcs.dao.menu.MenuMapper;
import com.starvincci.dcs.dao.user.UserMapper;
import com.starvincci.dcs.pojo.menu.UserMenu;
import com.starvincci.dcs.pojo.user.Roles;
import com.starvincci.dcs.pojo.user.UserRoleFile;
import com.starvincci.dcs.pojo.user.Users;
import com.starvincci.dcs.service.files.FilesService;
import com.starvincci.dcs.service.menu.MenuServiceImpl;
import com.starvincci.dcs.service.user.RoleServiceImpl;
import com.starvincci.dcs.service.user.UserRoleFileService;
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
    private RoleServiceImpl roleService;
    @Resource
    private UserRoleFileService userRoleFileService;
    @Resource
    private FilesService filesService;
    @Resource
    private MenuServiceImpl menuService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println("用户名-----"+s);
        Users users = new Users();
        users.setAccount(s);

        Users resultUser = userMapper.findByAccount(users);
        if (resultUser == null) {
            throw new UsernameNotFoundException("Username :" + s + "not found");
        }

        System.err.println(resultUser);
        List<UserMenu> userMenus=menuService.getUserMenuByUid(resultUser.getId());
        List<Roles> rolesList = roleService.getRoleByGroupId(resultUser.getRoleGroupId());
        List<UserRoleFile> roleFile = userRoleFileService.getAllByUserId(resultUser.getId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", resultUser);
        map.put("role", rolesList);
        map.put("menu",userMenus);
        map.put("roleFile",roleFile);

        MyUserDetails securityUser = new MyUserDetails();
        securityUser.setUsername(resultUser.getAccount());
        securityUser.setPassword(resultUser.getPassword());

        if (resultUser.getIsStatus() == 1) {
            securityUser.setEnabled(false);
        }else{
            securityUser.setEnabled(true);
        }

        securityUser.setObj(map);
        return securityUser;
    }
}
