package com.starvincci.dcs.dao.plan;

import com.starvincci.dcs.pojo.plan.Plan;
import com.starvincci.dcs.pojo.plan.PlanAuth;
import com.starvincci.dcs.pojo.plan.PlanCheck;
import com.starvincci.dcs.pojo.plan.PlanGather;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {

    /**
     * 添加认证计划
     * @param plan
     * @return
     */
    int addPlan(Plan plan);

    /**
     * 添加认证计划的认证时间等等
     * @param planAuth
     * @return
     */
    int addPlanAuth(PlanAuth planAuth);

    /**
     * 添加认证计划的收集资料时间等等
     * @param planGather
     * @return
     */
    int addPlanGather(PlanGather planGather);

    /**
     * 添加认证计划的稽核时间等等
     * @param planCheck
     * @return
     */
    int addPlanCheck(PlanCheck planCheck);


    /**
     * 修改 收集资料计划
     * @param planGather
     * @return
     */
    int updatePlanGather(PlanGather planGather);
    /**
     * 修改 审核认证计划
     * @param planAuth
     * @return
     */
    int updatePlanAuth(PlanAuth planAuth);

    /**
     * 修改 稽核计划
     * @param planCheck
     * @return
     */
    int updatePlanCheck(PlanCheck planCheck);


    /**
     * 修改计划 的稽核通过状态
     * @param plan
     * @return
     */
    int updatePlanPass(Plan plan);





    /**
     * 根据认证项目查询认证计划
     * @param dirId 如果为空 则 查询所有
     * @return
     */
    List<Plan> getPlanByDirId(Integer dirId);

    /**
     * 根据计划id查询认证信息
     * @param planId
     * @return
     */
    PlanAuth getPlanAuthById(Integer planId);
    /**
     * 根据计划id查询收集资料信息
     * @param planId
     * @return
     */
    PlanGather getPlanGatherById(Integer planId);

    /**
     * 根据计划id查询稽核信息
     * @param planId
     * @return
     */
    PlanCheck getPlanCheckById(Integer planId);

    /**
     * 最新的planId
     * @return
     */
    Plan getPlanTop();

    /**
     * 查询未完成计划中的计划认证内容是否有相同
     * @param content
     * @return
     */
    Plan getSamePlan(String content);

    /**
     * 清空值
     * @param planAuth
     * @return
     */
    int updatePlanAuthEmpty(PlanAuth planAuth);

    /**
     * 清空值
     * @param planCheck
     * @return
     */
    int updatePlanCheckEmpty(PlanCheck planCheck);

    /**
     * 更新认证计划带用户id
     * @param planAuth
     * @return
     */
    int updatePlanAuthWithUser(PlanAuth planAuth);

    /**
     * 更新稽核计划带用户id
     * @param planCheck
     * @return
     */
    int updatePlanCheckWithUser(PlanCheck planCheck);

    /**
     * 查询已完成的计划
     * @return
     */
    List<Plan> getPlanFinish(Integer dirId);


}
