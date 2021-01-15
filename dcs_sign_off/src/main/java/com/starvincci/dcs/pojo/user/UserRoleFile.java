package com.starvincci.dcs.pojo.user;

import com.starvincci.dcs.pojo.files.Files;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 文件权限
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleFile {
    private Integer id;
    private Integer userId;
    private Integer fileId;
    private String operation;
    private Files files;

}
