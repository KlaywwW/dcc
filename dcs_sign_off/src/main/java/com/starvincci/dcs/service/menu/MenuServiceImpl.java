package com.starvincci.dcs.service.menu;

import com.starvincci.dcs.dao.menu.MenuMapper;
import com.starvincci.dcs.pojo.Directory;
import com.starvincci.dcs.pojo.menu.Menu;
import com.starvincci.dcs.pojo.menu.UserMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;


    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.getMenuById(id);
    }

    @Override
    public List<UserMenu> getUserMenuByUid(Integer userId) {
        return menuMapper.getUserMenuByUid(userId);
    }
}
