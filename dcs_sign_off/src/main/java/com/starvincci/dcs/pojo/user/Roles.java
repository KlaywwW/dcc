package com.starvincci.dcs.pojo.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 权限
 */
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Roles {

  private Integer roleId;
  private String roleName;
  private String roleCode;
  private UserRoleGroup userRoleGroup;

}
