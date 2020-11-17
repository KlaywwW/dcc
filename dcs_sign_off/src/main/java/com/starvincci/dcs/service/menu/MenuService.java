package com.starvincci.dcs.service.menu;

import com.starvincci.dcs.pojo.Directory;
import com.starvincci.dcs.pojo.menu.Menu;
import com.starvincci.dcs.pojo.menu.UserMenu;

import java.util.List;

public interface MenuService {

    Menu getMenuById(Integer id);
    List<UserMenu> getUserMenuByUid(Integer userId);
}
