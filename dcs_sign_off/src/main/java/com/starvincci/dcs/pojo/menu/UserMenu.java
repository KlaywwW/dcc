package com.starvincci.dcs.pojo.menu;

import com.starvincci.dcs.pojo.user.Users;

public class UserMenu {

    private Integer id;
    private Integer mid;
    private Integer uid;

    private  Menu menu;
    private Users users;

    @Override
    public String toString() {
        return "UserMenu{" +
                "id=" + id +
                ", mid=" + mid +
                ", uid=" + uid +
                ", menu=" + menu +
                ", users=" + users +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
