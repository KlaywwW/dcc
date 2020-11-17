package com.starvincci.dcs.pojo.menu;

public class Menu {
    private Integer id;
    private String menuPath; //菜单地址
    private String menuName; //菜单名称

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuPath='" + menuPath + '\'' +
                ", menuName='" + menuName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
