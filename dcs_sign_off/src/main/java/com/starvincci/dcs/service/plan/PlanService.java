package com.starvincci.dcs.service.plan;

import com.starvincci.dcs.pojo.plan.Plan;
import com.starvincci.dcs.pojo.plan.PlanAuth;
import com.starvincci.dcs.pojo.plan.PlanCheck;
import com.starvincci.dcs.pojo.plan.PlanGather;

import java.util.List;

public interface PlanService {
    int addPlan(Plan plan);

    int addPlanAuth(PlanAuth planAuth);

    int addPlanGather(PlanGather planGather);

    int addPlanCheck(PlanCheck planCheck);

    List<Plan> getPlanByDirId(Integer dirId);

    PlanAuth getPlanAuthById(Integer planId);

    PlanGather getPlanGatherById(Integer planId);

    PlanCheck getPlanCheckById(Integer planId);

    Plan getPlanTop();

    Plan getSamePlan(String content);

    int updatePlanGather(PlanGather planGather);
    int updatePlanAuth(PlanAuth planAuth);
    int updatePlanCheck(PlanCheck planCheck);

    int updatePlanPass(Plan plan);


    int updatePlanAuthEmpty(PlanAuth planAuth);
    int updatePlanCheckEmpty(PlanCheck planCheck);
    int updatePlanAuthWithUser(PlanAuth planAuth);
    int updatePlanCheckWithUser(PlanCheck planCheck);

    List<Plan> getPlanFinish(Integer dirId);
}
