package com.starvincci.dcs.pojo.plan;

import com.starvincci.dcs.pojo.user.Users;

import java.util.List;

public class PlanAuth {

    private Integer id;//主键id
    private String  authPlanTime;//计划认证时间
    private String userId;//计划认证人员
    private String authActualTime;//实际认证时间
    private Integer authUserId;//完成用户id
    private Integer planId;//计划认证内容id
    private List<Users> users;//计划认证用户对象
    private Users authUser;//认证完成用户对象
    private Plan plan;

    public Users getAuthUser() {
        return authUser;
    }

    public void setAuthUser(Users authUser) {
        this.authUser = authUser;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "PlanAuth{" +
                "id=" + id +
                ", authPlanTime='" + authPlanTime + '\'' +
                ", userId=" + userId +
                ", authActualTime='" + authActualTime + '\'' +
                ", authUserId=" + authUserId +
                ", planId=" + planId +
                ", users=" + users +
                ", plan=" + plan +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthPlanTime() {
        return authPlanTime;
    }

    public void setAuthPlanTime(String authPlanTime) {
        this.authPlanTime = authPlanTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthActualTime() {
        return authActualTime;
    }

    public void setAuthActualTime(String authActualTime) {
        this.authActualTime = authActualTime;
    }

    public Integer getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(Integer authUserId) {
        this.authUserId = authUserId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
