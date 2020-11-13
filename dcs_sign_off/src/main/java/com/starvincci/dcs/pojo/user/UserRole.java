package com.starvincci.dcs.pojo.user;


import java.util.List;

public class UserRole {

  private Integer id;
  private Integer userId;
  private Integer roleId;
  private Roles roles;
  private Users users;
  private List<UserRoleFile> userRoleFileList;

  public Users getUsers() {
    return users;
  }

  public void setUsers(Users users) {
    this.users = users;
  }

  public List<UserRoleFile> getUserRoleFileList() {
    return userRoleFileList;
  }

  public void setUserRoleFileList(List<UserRoleFile> userRoleFileList) {
    this.userRoleFileList = userRoleFileList;
  }

  public Roles getRoles() {
    return roles;
  }

  public void setRoles(Roles roles) {
    this.roles = roles;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

}
