package com.starvincci.dcs.pojo.plan;

import com.starvincci.dcs.pojo.user.Users;

import java.util.List;

public class PlanCheck {
    private Integer id;//主键id
    private String  checkPlanTime;//计划稽核时间
    private String userId;//计划稽核人员
    private String checkActualTime;//实际稽核时间
    private Integer checkUserId;//完成用户id
    private Integer planId;//计划认证内容id
    private List<Users> users;//计划稽核用户对象
    private Users checkUser;//稽核完成用户对象
    private Plan plan;

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Users getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(Users checkUser) {
        this.checkUser = checkUser;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "PlanCheck{" +
                "id=" + id +
                ", checkPlanTime='" + checkPlanTime + '\'' +
                ", userId=" + userId +
                ", checkActualTime='" + checkActualTime + '\'' +
                ", checkUserId=" + checkUserId +
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

    public String getCheckPlanTime() {
        return checkPlanTime;
    }

    public void setCheckPlanTime(String checkPlanTime) {
        this.checkPlanTime = checkPlanTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCheckActualTime() {
        return checkActualTime;
    }

    public void setCheckActualTime(String checkActualTime) {
        this.checkActualTime = checkActualTime;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
