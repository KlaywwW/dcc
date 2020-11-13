package com.starvincci.dcs.service.plan;

import com.starvincci.dcs.dao.plan.PlanMapper;
import com.starvincci.dcs.pojo.plan.Plan;
import com.starvincci.dcs.pojo.plan.PlanAuth;
import com.starvincci.dcs.pojo.plan.PlanCheck;
import com.starvincci.dcs.pojo.plan.PlanGather;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Resource
    private PlanMapper planMapper;

    @Override
    public int addPlan(Plan plan) {
        return planMapper.addPlan(plan);
    }

    @Override
    public int addPlanAuth(PlanAuth planAuth) {
        return planMapper.addPlanAuth(planAuth);
    }

    @Override
    public int addPlanGather(PlanGather planGather) {
        return planMapper.addPlanGather(planGather);
    }

    @Override
    public int addPlanCheck(PlanCheck planCheck) {
        return planMapper.addPlanCheck(planCheck);
    }

    @Override
    public List<Plan> getPlanByDirId(Integer dirId) {
        return planMapper.getPlanByDirId(dirId);
    }

    @Override
    public PlanAuth getPlanAuthById(Integer planId) {
        return planMapper.getPlanAuthById(planId);
    }

    @Override
    public PlanGather getPlanGatherById(Integer planId) {
        return planMapper.getPlanGatherById(planId);
    }

    @Override
    public PlanCheck getPlanCheckById(Integer planId) {
        return planMapper.getPlanCheckById(planId);
    }

    @Override
    public Plan getPlanTop() {
        return planMapper.getPlanTop();
    }

    @Override
    public Plan getSamePlan(String content) {
        return planMapper.getSamePlan(content);
    }

    @Override
    public int updatePlanGather(PlanGather planGather) {
        return planMapper.updatePlanGather(planGather);
    }

    @Override
    public int updatePlanAuth(PlanAuth planAuth) {
        return planMapper.updatePlanAuth(planAuth);
    }

    @Override
    public int updatePlanCheck(PlanCheck planCheck) {
        return planMapper.updatePlanCheck(planCheck);
    }

    @Override
    public int updatePlanPass(Plan plan) {
        return planMapper.updatePlanPass(plan);
    }

    @Override
    public int updatePlanAuthEmpty(PlanAuth planAuth) {
        return planMapper.updatePlanAuthEmpty(planAuth);
    }

    @Override
    public int updatePlanCheckEmpty(PlanCheck planCheck) {
        return planMapper.updatePlanCheckEmpty(planCheck);
    }

    @Override
    public int updatePlanAuthWithUser(PlanAuth planAuth) {
        return planMapper.updatePlanAuthWithUser(planAuth);
    }

    @Override
    public int updatePlanCheckWithUser(PlanCheck planCheck) {
        return planMapper.updatePlanCheckWithUser(planCheck);
    }

    @Override
    public List<Plan> getPlanFinish(Integer dirId) {
        return planMapper.getPlanFinish(dirId);
    }
}
