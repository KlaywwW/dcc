package com.starvincci.dcs.pojo.plan;

import com.starvincci.dcs.pojo.user.Users;

public class PlanGather {
    private Integer id;//主键id
    private String  gatherPlanTime;//计划收集时间
    private Integer userId;//计划收集人员
    private Integer planId;//计划认证内容id
    private Users users;//计划收集资料用户对象
    private Users gatherUser;//完成用户对象
    private Plan plan;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getGatherUser() {
        return gatherUser;
    }

    public void setGatherUser(Users gatherUser) {
        this.gatherUser = gatherUser;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "PlanGather{" +
                "id=" + id +
                ", gatherPlanTime='" + gatherPlanTime + '\'' +
                ", userId=" + userId +
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

    public String getGatherPlanTime() {
        return gatherPlanTime;
    }

    public void setGatherPlanTime(String gatherPlanTime) {
        this.gatherPlanTime = gatherPlanTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
