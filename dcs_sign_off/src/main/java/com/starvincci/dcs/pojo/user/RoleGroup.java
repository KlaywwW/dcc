package com.starvincci.dcs.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 权限组
 */
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class RoleGroup {
    private Integer groupId;
    private String groupName;
}
