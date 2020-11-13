package com.starvincci.dcs.pojo.files;


import com.starvincci.dcs.pojo.user.UserRoleFile;
import com.starvincci.dcs.pojo.user.Department;

import java.util.List;

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
    private List<UserRoleFile> userRoleFileList;
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<UserRoleFile> getUserRoleFileList() {
        return userRoleFileList;
    }

    public void setUserRoleFileList(List<UserRoleFile> userRoleFileList) {
        this.userRoleFileList = userRoleFileList;
    }

    @Override
    public String toString() {
        return "Files{" +
                "filesId=" + filesId +
                ", filesName='" + filesName + '\'' +
                ", filesPath='" + filesPath + '\'' +
                ", cdate='" + cdate + '\'' +
                ", cuser='" + cuser + '\'' +
                ", departmentId=" + departmentId +
                ", isoNo='" + isoNo + '\'' +
                ", version=" + version +
                ", originalFileId=" + originalFileId +
                ", versionNo=" + versionNo +
                ", dirId=" + dirId +
                ", userId=" + userId +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDirId() {
        return dirId;
    }

    public void setDirId(Integer dirId) {
        this.dirId = dirId;
    }

    public Integer getFilesId() {
        return filesId;
    }

    public void setFilesId(Integer filesId) {
        this.filesId = filesId;
    }

    public String getFilesName() {
        return filesName;
    }

    public void setFilesName(String filesName) {
        this.filesName = filesName;
    }

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getIsoNo() {
        return isoNo;
    }

    public void setIsoNo(String isoNo) {
        this.isoNo = isoNo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getOriginalFileId() {
        return originalFileId;
    }

    public void setOriginalFileId(Integer originalFileId) {
        this.originalFileId = originalFileId;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }
}
