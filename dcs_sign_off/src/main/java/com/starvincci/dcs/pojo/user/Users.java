package com.starvincci.dcs.pojo.user;


import com.starvincci.dcs.pojo.menu.UserMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Users {

  private Integer id;
  private String account;
  private String password;
  private Integer departmentId;
  private String username;
  private Integer age;
  private Integer isStatus;
  private Integer roleGroupId;
  private Department department;
  private RoleGroup roleGroup;
  private HashMap<String,Object> userMap;

}
