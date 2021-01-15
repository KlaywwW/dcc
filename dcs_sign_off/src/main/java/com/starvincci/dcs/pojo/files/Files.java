package com.starvincci.dcs.pojo.files;


import com.starvincci.dcs.pojo.user.UserRoleFile;
import com.starvincci.dcs.pojo.user.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Files {

    private Integer filesId;//文件id
    private String filesName;//文件名称
    private String filesPath;//文件路径
    private String cdate;//文件创建时间
    private String cuser;//文件创建用户
    private Integer departmentId;//所属部门id
    private String isoNo;//ISO编号
    private Integer version;//最新版本文件id
    private Integer originalFileId;//原始文件id
    private Integer versionNo;//版本号
    private Integer dirId;//目录id
    private Integer userId;//用户id
    private Department department;
}
