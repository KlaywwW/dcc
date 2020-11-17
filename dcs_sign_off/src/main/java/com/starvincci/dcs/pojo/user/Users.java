package com.starvincci.dcs.pojo.user;


import com.starvincci.dcs.pojo.menu.UserMenu;

import java.util.List;

public class Users {

  private Integer id;
  private String account;
  private String password;
  private Integer departmentId;
  private String username;
  private Integer age;
  private Integer isStatus;
  private Integer dirId;
  private Department department;
  private List<UserRole> userRoleList;
  private List<UserMenu> userMenuList;

  public List<UserMenu> getUserMenuList() {
    return userMenuList;
  }

  public void setUserMenuList(List<UserMenu> userMenuList) {
    this.userMenuList = userMenuList;
  }

  public List<UserRole> getUserRoleList() {
    return userRoleList;
  }

  public void setUserRoleList(List<UserRole> userRoleList) {
    this.userRoleList = userRoleList;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Integer getIsStatus(){ return isStatus;}

  public void setIsStatus(Integer isStatus){ this.isStatus=isStatus; }

  public Integer getDirId() {
    return dirId;
  }

  public void setDirId(Integer dirId) {
    this.dirId = dirId;
  }
}
