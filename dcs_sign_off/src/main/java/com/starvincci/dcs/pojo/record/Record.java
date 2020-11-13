package com.starvincci.dcs.pojo.record;

import com.starvincci.dcs.pojo.files.Files;
import com.starvincci.dcs.pojo.user.Users;

public class Record {
    private Integer id;
    private String applyName;
    private String applyDate;
    private String applyContent;
    private Integer applyStatus;//1.待通过，2.通过，3.未通过
    private String applyPassDate;
    private String operator;
    private Integer inForce;
    private Integer userId;
    private Integer fileId;
    private Files files;
    private Users users;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", applyName='" + applyName + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", applyContent='" + applyContent + '\'' +
                ", applyStatus=" + applyStatus +
                ", applyPassDate='" + applyPassDate + '\'' +
                ", operator='" + operator + '\'' +
                ", inForce=" + inForce +
                ", userId=" + userId +
                ", fileId=" + fileId +
                ", files=" + files +
                '}';
    }


    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyPassDate() {
        return applyPassDate;
    }

    public void setApplyPassDate(String applyPassDate) {
        this.applyPassDate = applyPassDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getInForce() {
        return inForce;
    }

    public void setInForce(Integer inForce) {
        this.inForce = inForce;
    }
}
