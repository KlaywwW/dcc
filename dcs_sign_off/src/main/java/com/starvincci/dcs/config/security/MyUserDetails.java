package com.starvincci.dcs.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;


/**
 * 实现了UserDetails接口，只留必需的属性，也可添加自己需要的属性
 */
public class MyUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    //登录用户名
    private String username;
    //登录密码
    private String password;
    //角色集合
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 指示用户是启用还是禁用。禁用的用户不能验证
     * true 启用 false 禁用
     */
    private boolean enabled;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    //用户其他属性
    private HashMap<String,Object> obj;

    public HashMap<String, Object> getObj() {
        return obj;
    }

    public void setObj(HashMap<String, Object> obj) {
        this.obj = obj;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}

