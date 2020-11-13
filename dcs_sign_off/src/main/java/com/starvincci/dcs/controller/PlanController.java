package com.starvincci.dcs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starvincci.dcs.pojo.Directory;
import com.starvincci.dcs.pojo.plan.Plan;
import com.starvincci.dcs.pojo.plan.PlanAuth;
import com.starvincci.dcs.pojo.plan.PlanCheck;
import com.starvincci.dcs.pojo.plan.PlanGather;
import com.starvincci.dcs.pojo.user.Users;
import com.starvincci.dcs.service.DirectoryServiceImpl;
import com.starvincci.dcs.service.plan.PlanServiceImpl;
import com.starvincci.dcs.service.user.UserServiceImpl;
import com.starvincci.dcs.utils.CalendarUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanController {

    @Resource
    private PlanServiceImpl planService;
    @Resource
    private DirectoryServiceImpl directoryService;
    @Resource
    private UserServiceImpl userService;

    @GetMapping("/getDir")
    private List<Directory> getDir() {
        return directoryService.getAllDir();
    }

    /**
     * 1.添加计划
     * 2.添加收集资料的人员用户id 计划送审时间 计划id
     * 3.添加审核认证 的计划时间  审核认证人员id的字符串 计划id
     *
     * @param strs
     * @return
     */
    @PostMapping("/addPlan")
    public String addPlan(@RequestBody String strs) {
        JSONObject jsonObject = JSON.parseObject(strs);
        Plan plan = new Plan();
        plan.setDirId(jsonObject.getInteger("dirId"));
        plan.setContent(jsonObject.getString("content"));
        plan.setUserId(jsonObject.getInteger("userId"));
        plan.setIsFinish(1);

        Plan samePlan = planService.getSamePlan(plan.getContent());
        if (samePlan != null) {
            return "添加失败,存在相同认证内容计划";
        }
        Object[] userId = jsonObject.getJSONArray("authUsers").toArray();
        StringBuffer sb = new StringBuffer();
        for (Object item : userId) {
            sb.append(item).append(",");
        }

        int res = planService.addPlan(plan);
        if (res == 1) {

            PlanGather planGather = new PlanGather();
            planGather.setGatherPlanTime(jsonObject.getString("time"));
            Plan resPlan = planService.getPlanTop();
            planGather.setPlanId(resPlan.getId());
            planGather.setUserId(jsonObject.getInteger("userId"));

            System.err.println(planGather.toString());

            int resGather = planService.addPlanGather(planGather);

            PlanAuth planAuth = new PlanAuth();
            planAuth.setAuthPlanTime(jsonObject.getString("time"));
            planAuth.setPlanId(resPlan.getId());
            planAuth.setUserId(sb.toString());

            System.err.println(planAuth.toString());
            int resAuth = planService.addPlanAuth(planAuth);
            if (resGather == 1 && resAuth == 1) {
                return "添加成功";
            }
        }
        return "添加失败";
    }

    @GetMapping("/getPlan")
    public HashMap<String, Object> getPlan() {

        HashMap<String, Object> hashMap = new HashMap<>();
        List<Plan> resList = new ArrayList<>();
        List<Directory> directoryList = directoryService.getAllDir();
        for (Directory directory : directoryList) {
            List<Plan> planList = planService.getPlanByDirId(directory.getId());
            for (Plan plan : planList) {
                plan.setDirectory(directoryService.getDirById(plan.getDirId()));
                plan.setUsers(userService.getUserById(plan.getUserId()));
                plan.setPlanGather(planService.getPlanGatherById(plan.getId()));

                PlanAuth planAuth = planService.getPlanAuthById(plan.getId());
                if (planAuth != null) {
                    String[] authStrs = planAuth.getUserId().split(",");
                    List<Users> authUsers = new ArrayList<>();
                    for (String item : authStrs) {
                        Integer userId = Integer.parseInt(item);
                        authUsers.add(userService.getUserById(userId));
                    }
                    planAuth.setUsers(authUsers);
                }
                plan.setPlanAuth(planAuth);


                PlanCheck planCheck = planService.getPlanCheckById(plan.getId());
                if (planCheck != null) {
                    String[] checkStrs = planCheck.getUserId().split(",");
                    List<Users> checkUsers = new ArrayList<>();
                    for (String item : checkStrs) {
                        Integer userId = Integer.parseInt(item);
                        checkUsers.add(userService.getUserById(userId));
                    }
                    planCheck.setUsers(checkUsers);
                }
                plan.setPlanCheck(planCheck);
                resList.add(plan);
            }
        }

        hashMap.put("data", resList);
        hashMap.put("count", resList.size());
        return hashMap;
    }


    /**
     * 暂时停用
     *
     * @param strs
     * @return
     */
    @PostMapping("/addPlanAuth")
    public String addPlanAuth(@RequestBody String strs) {

        JSONObject jsonObject = JSON.parseObject(strs);

        PlanAuth planAuth = new PlanAuth();
        planAuth.setAuthPlanTime(jsonObject.getString("time"));
        planAuth.setPlanId(jsonObject.getInteger("planId"));
//        planAuth.setUserId(jsonObject.getInteger("userId"));

        PlanAuth resPlanAuth = planService.getPlanAuthById(planAuth.getPlanId());

        if (resPlanAuth != null) {
            PlanAuth planAuthUpdate = new PlanAuth();
            planAuthUpdate.setAuthPlanTime(planAuth.getAuthPlanTime());
            planAuthUpdate.setPlanId(planAuth.getPlanId());

//            planAuthUpdate.setUserId(jsonObject.getInteger("userId"));

            int res = planService.updatePlanAuthWithUser(planAuthUpdate);
            if (res == 1) {
                return "认证计划添加成功";
            }
        }


        int res = planService.addPlanAuth(planAuth);
        if (res == 1) {
            return "认证计划添加成功";

        }
        System.err.println(planAuth.toString());


        return "添加失败";
    }


    /**
     * 添加稽核
     *
     * @param strs
     * @return
     */
    @PostMapping("/addPlanCheck")
    public String addPlanCheck(@RequestBody String strs) {

        JSONObject jsonObject = JSON.parseObject(strs);
        PlanCheck planCheck = new PlanCheck();
        planCheck.setCheckPlanTime(jsonObject.getString("time"));
        planCheck.setPlanId(jsonObject.getInteger("planId"));
        Object[] userId = jsonObject.getJSONArray("checkUsers").toArray();
        StringBuffer sb = new StringBuffer();
        for (Object item : userId) {
            sb.append(item).append(",");
        }
        planCheck.setUserId(sb.toString());


        int res = planService.addPlanCheck(planCheck);
        if (res == 1) {
            return "稽核计划添加成功";
        }

        return "添加失败";
    }

    /**
     * 停用
     *
     * @param strs
     * @return
     */
    @PostMapping("/updatePlanGather")
    public String updatePlanGather(@RequestBody String strs) {
        JSONObject jsonObject = JSON.parseObject(strs);

        PlanGather planGather = new PlanGather();
        planGather.setPlanId(jsonObject.getInteger("planId"));
        planGather.setGatherPlanTime(jsonObject.getString("time"));
        int updateRes = planService.updatePlanGather(planGather);

        if (updateRes == 1) {
            return "修改成功";
        }
        return "修改失败";
    }

    @PostMapping("/updatePlanAuth")
    public String updatePlanAuth(@RequestBody String strs) {
        JSONObject jsonObject = JSON.parseObject(strs);

        PlanAuth planAuth = new PlanAuth();
        planAuth.setPlanId(jsonObject.getInteger("planId"));
        planAuth.setAuthPlanTime(jsonObject.getString("time"));
        int updateRes = planService.updatePlanAuth(planAuth);
        if (updateRes == 1) {
            return "修改成功";
        }
        return "修改失败";
    }

    @PostMapping("/updatePlanCheck")
    public String updatePlanCheck(@RequestBody String strs) {
        JSONObject jsonObject = JSON.parseObject(strs);

        PlanCheck planCheck = new PlanCheck();
        planCheck.setPlanId(jsonObject.getInteger("planId"));
        planCheck.setCheckPlanTime(jsonObject.getString("time"));
        int updateRes = planService.updatePlanCheck(planCheck);
        if (updateRes == 1) {
            return "修改成功";
        }
        return "修改失败";
    }

    @PostMapping("/passCheck")
    public String passCheck(@RequestBody String strs) {
        JSONObject jsonObject = JSON.parseObject(strs);
        PlanCheck planCheck = new PlanCheck();
        planCheck.setPlanId(jsonObject.getInteger("planId"));
        planCheck.setCheckUserId(jsonObject.getInteger("userId"));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        planCheck.setCheckActualTime(sf.format(new Date()));

        int res = planService.updatePlanCheck(planCheck);
        if (res == 1) {
            Plan plan = new Plan();
            plan.setId(jsonObject.getInteger("planId"));
            int resPlan = planService.updatePlanPass(plan);
            if (resPlan == 1) {
                return "稽核通过";
            }
        }


        return "通过";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam("planId") Integer planId,
                         @RequestParam("userId") Integer userId) {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PlanAuth planAuth = new PlanAuth();
        planAuth.setAuthActualTime(sf.format(new Date()));
        planAuth.setPlanId(planId);
        planAuth.setAuthUserId(userId);

        int res = planService.updatePlanAuth(planAuth);
        if (res == 1) {
            return "确认成功";
        }

        return "确认失败";
    }

    @GetMapping("/getPlanByDir")
    public HashMap<String, Object> getPlanByDir(@RequestParam("dirId") Integer dirId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Plan> resList = new ArrayList<>();
        if (dirId == 0) {
            return getPlan();
        }
        List<Plan> planList = planService.getPlanByDirId(directoryService.getDirById(dirId).getId());
        for (Plan plan : planList) {
            plan.setDirectory(directoryService.getDirById(plan.getDirId()));
            plan.setUsers(userService.getUserById(plan.getUserId()));
            plan.setPlanGather(planService.getPlanGatherById(plan.getId()));

            PlanAuth planAuth = planService.getPlanAuthById(plan.getId());
            if (planAuth != null) {
                String[] authStrs = planAuth.getUserId().split(",");
                List<Users> authUsers = new ArrayList<>();
                for (String item : authStrs) {
                    Integer userId = Integer.parseInt(item);
                    authUsers.add(userService.getUserById(userId));
                }
                planAuth.setUsers(authUsers);
            }
            plan.setPlanAuth(planAuth);


            PlanCheck planCheck = planService.getPlanCheckById(plan.getId());
            if (planCheck != null) {
                String[] checkStrs = planCheck.getUserId().split(",");
                List<Users> checkUsers = new ArrayList<>();
                for (String item : checkStrs) {
                    Integer userId = Integer.parseInt(item);
                    checkUsers.add(userService.getUserById(userId));
                }
                planCheck.setUsers(checkUsers);
            }
            plan.setPlanCheck(planCheck);
            resList.add(plan);
        }
        hashMap.put("data", resList);
        hashMap.put("num", resList.size());

        return hashMap;
    }

    @GetMapping("/getPlanFinish")
    public HashMap<String, Object> getPlanFinish() {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Plan> resList = new ArrayList<>();
        List<Directory> directoryList = directoryList = directoryService.getAllDir();
        for (Directory directory : directoryList) {
            List<Plan> planList = planService.getPlanFinish(directory.getId());
            for (Plan plan : planList) {
                plan.setDirectory(directoryService.getDirById(plan.getDirId()));
                plan.setUsers(userService.getUserById(plan.getUserId()));
                plan.setPlanGather(planService.getPlanGatherById(plan.getId()));

                PlanAuth planAuth = planService.getPlanAuthById(plan.getId());
                if (planAuth != null) {
                    String[] authStrs = planAuth.getUserId().split(",");
                    List<Users> authUsers = new ArrayList<>();
                    for (String item : authStrs) {
                        Integer userId = Integer.parseInt(item);
                        authUsers.add(userService.getUserById(userId));
                    }
                    planAuth.setUsers(authUsers);
                }
                plan.setPlanAuth(planAuth);


                PlanCheck planCheck = planService.getPlanCheckById(plan.getId());
                if (planCheck != null) {
                    String[] checkStrs = planCheck.getUserId().split(",");
                    List<Users> checkUsers = new ArrayList<>();
                    for (String item : checkStrs) {
                        Integer userId = Integer.parseInt(item);
                        checkUsers.add(userService.getUserById(userId));
                    }
                    planCheck.setUsers(checkUsers);
                }
                plan.setPlanCheck(planCheck);
                resList.add(plan);
            }
        }
        hashMap.put("data", resList);
        hashMap.put("num", resList.size());
        return hashMap;
    }


    /**
     * 1. 查询所有未完成计划
     * 2. 1->判断planAuth 的 计划时间不为空， 完成时间为空，
     * 2->再传入用户id 如果存在直接退出循环保存 到集合中
     * <p>
     * 3->如果1和2的条件不满足进入下一次判断
     * 4->判断planCheck 的计划时间不为空， 完成时间为空
     * 同2
     * <p>
     * 3.查询到的就是登陆用户所对应的认证计划
     */

    @GetMapping("getPlanByUser")
    public HashMap<String, Object> getPlanByUser(@RequestParam("userId") Integer userId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Plan> resList = getUserPlan(userId);
        hashMap.put("data", resList);
        hashMap.put("count", resList.size());

        return hashMap;
    }

    /**
     * 获取不同认证项目在进行中的数量
     *
     * @param dirId
     * @return
     */
    @GetMapping("/getPlanNum")
    public HashMap<String, Object> getPlanNum(@RequestParam("dirId") Integer dirId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Plan> planList = planService.getPlanByDirId(directoryService.getDirById(dirId).getId());

        hashMap.put("num", planList.size());
        return hashMap;
    }

    /**
     * 根据用户id查询所要今天需要进行完成的事项
     *
     * @return
     */
    @GetMapping("/nowPlan")
    public HashMap<String, Object> nowPlan(@RequestParam("userId") Integer userId) {
        HashMap<String, Object> hashMap = new HashMap<>();

        List<Plan> planList = getUserPlan(userId);

        List<Plan> plans = new ArrayList<>();
        for (Plan plan : planList) {
            PlanAuth planAuth = plan.getPlanAuth();
            PlanCheck planCheck = plan.getPlanCheck();
            int authCount = 0;
            int checkCount = 0;
            if (planAuth != null && planCheck == null) {
                if (planAuth.getAuthPlanTime() != null && (planAuth.getAuthActualTime() == null || "".equals(planAuth.getAuthActualTime()))) {
                    if (new CalendarUtil().isSame(planAuth.getAuthPlanTime())) {
                        authCount++;
                    }
                }
            } else if (planAuth != null && planCheck != null) {
                if (planCheck.getCheckPlanTime() != null && (planCheck.getCheckActualTime() == null || "".equals(planCheck.getCheckActualTime()))) {
                    if (new CalendarUtil().isSame(planCheck.getCheckPlanTime())) {
                        checkCount++;
                    }
                }
            }
            if (authCount > 0 || checkCount > 0) {
                plans.add(plan);
            }

        }
        hashMap.put("data", plans);
        hashMap.put("count", plans.size());

        return hashMap;
    }

    public List<Plan> getUserPlan(Integer userId) {
        List<Plan> resList = new ArrayList<>();
        List<Directory> directoryList = directoryService.getAllDir();
        for (Directory directory : directoryList) {
            List<Plan> planList = planService.getPlanByDirId(directory.getId());
            for (Plan plan : planList) {
                plan.setDirectory(directoryService.getDirById(plan.getDirId()));
                plan.setUsers(userService.getUserById(plan.getUserId()));
                plan.setPlanGather(planService.getPlanGatherById(plan.getId()));

                PlanAuth planAuth = planService.getPlanAuthById(plan.getId());
                PlanCheck planCheck = planService.getPlanCheckById(plan.getId());
                int authCount = 0;
                int checkCount = 0;
                if (planAuth != null && planCheck == null) {
                    if (planAuth.getAuthPlanTime() != null && (planAuth.getAuthActualTime() == null || "".equals(planAuth.getAuthActualTime()))) {
                        String[] userIds = planAuth.getUserId().split(",");
                        List<Users> authUsers = new ArrayList<>();
                        for (String item : userIds) {
                            if (Integer.parseInt(item) == userId) {
                                authCount++;
                            }
                            authUsers.add(userService.getUserById(Integer.parseInt(item)));
                        }
                        planAuth.setUsers(authUsers);
                        if (authCount > 0) {
                            plan.setPlanAuth(planAuth);
                        }
                    }
                } else if (planAuth != null && planCheck != null) {
                    if (planCheck.getCheckPlanTime() != null && (planCheck.getCheckActualTime() == null || "".equals(planCheck.getCheckActualTime()))) {
                        String[] userIds = planCheck.getUserId().split(",");
                        List<Users> checkUsers = new ArrayList<>();
                        for (String item : userIds) {
                            if (Integer.parseInt(item) == userId) {
                                checkCount++;
                            }
                            checkUsers.add(userService.getUserById(Integer.parseInt(item)));
                        }
                        planCheck.setUsers(checkUsers);
                        if (checkCount > 0) {

                            String[] userIdsAuth = planAuth.getUserId().split(",");
                            List<Users> authUsers = new ArrayList<>();
                            for (String item : userIdsAuth) {
                                authUsers.add(userService.getUserById(Integer.parseInt(item)));
                            }
                            planAuth.setUsers(authUsers);
                            plan.setPlanAuth(planAuth);

                            plan.setPlanCheck(planCheck);
                        }
                    }
                }


                if (authCount > 0 || checkCount > 0) {
                    resList.add(plan);
                }
            }
        }
        return resList;
    }


}
