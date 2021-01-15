package com.starvincci.dcs.dao.menu;

import com.starvincci.dcs.pojo.menu.Menu;
import com.starvincci.dcs.pojo.menu.UserMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface MenuMapper {


    /**
     * 根据菜单id查找对应菜单信息
     * @param id
     * @return
     */
    Menu getMenuById(Integer id);

    /**
     * 根据用户查找所对应菜单
     * @param userId
     * @return
     */
    List<UserMenu> getUserMenuByUid(Integer userId);



    /**
     * 获取所有的菜单
     * @return
     */
    List<Menu> getAllMenus();

    /**
     * 查询当前用户是否存在相同的菜单
     * @param mid
     * @param uid
     * @return
     */
    UserMenu getSameUserMenu(@Param("mid")Integer mid,@Param("uid")Integer uid);
    /**
     * 添加用户菜单权限
     * @param mid
     * @param uid
     * @return
     */
    int addUserMenu(Integer mid,Integer uid);

    /**
     * 删除用户的某个菜单
     * @param mid
     * @param uid
     * @return
     */
    int delUserMenu(@Param("mid")Integer mid,@Param("uid")Integer uid);
}
