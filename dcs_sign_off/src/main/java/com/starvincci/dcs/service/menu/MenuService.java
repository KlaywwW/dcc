package com.starvincci.dcs.service.menu;

import com.starvincci.dcs.pojo.Directory;
import com.starvincci.dcs.pojo.menu.Menu;
import com.starvincci.dcs.pojo.menu.UserMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuService {

    Menu getMenuById(Integer id);
    List<UserMenu> getUserMenuByUid(Integer userId);
    int addUserMenu(Integer mid,Integer uid);

    /**
     * 获取所有的菜单
     * @return
     */
    List<Menu> getAllMenus();
    /**
     * 删除用户的某个菜单
     * @param mid
     * @param uid
     * @return
     */
    int delUserMenu(@Param("mid")Integer mid, @Param("uid")Integer uid);
    /**
     * 查询当前用户是否存在相同的菜单
     * @param mid
     * @param uid
     * @return
     */
    UserMenu getSameUserMenu(@Param("mid")Integer mid,@Param("uid")Integer uid);
}
