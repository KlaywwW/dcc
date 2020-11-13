package com.starvincci.dcs.pojo.user;

import com.starvincci.dcs.pojo.files.Files;

public class UserRoleFile {
    private Integer id;
    private Integer userRoleId;
    private Integer fileId;
    private Integer inForce;
    private Files files;
    private UserRole userRole;


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getInForce() {
        return inForce;
    }

    public void setInForce(Integer inForce) {
        this.inForce = inForce;
    }

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }
}
