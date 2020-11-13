package com.starvincci.dcs.pojo.plan;

import com.starvincci.dcs.pojo.Directory;
import com.starvincci.dcs.pojo.user.Users;

public class Plan {
    //主键id
    private Integer id;
    //计划认证内容
    private String content;
    //认证项目
    private Integer dirId;
    //添加人
    private Integer userId;
    private Integer isFinish;
    private PlanAuth planAuth;
    private PlanCheck planCheck;
    private PlanGather planGather;
    private Users users;
    private Directory directory;

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public PlanAuth getPlanAuth() {
        return planAuth;
    }

    public void setPlanAuth(PlanAuth planAuth) {
        this.planAuth = planAuth;
    }

    public PlanCheck getPlanCheck() {
        return planCheck;
    }

    public void setPlanCheck(PlanCheck planCheck) {
        this.planCheck = planCheck;
    }

    public PlanGather getPlanGather() {
        return planGather;
    }

    public void setPlanGather(PlanGather planGather) {
        this.planGather = planGather;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", dirId=" + dirId +
                ", userId=" + userId +
                ", isFinish=" + isFinish +
                ", planAuth=" + planAuth +
                ", planCheck=" + planCheck +
                ", planGather=" + planGather +
                ", users=" + users +
                ", directory=" + directory +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDirId() {
        return dirId;
    }

    public void setDirId(Integer dirId) {
        this.dirId = dirId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }
}
