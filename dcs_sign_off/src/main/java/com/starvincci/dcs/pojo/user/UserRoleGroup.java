package com.starvincci.dcs.pojo.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserRoleGroup {

  private Integer id;
  private Integer roleId;
  private Integer groupId;


}
