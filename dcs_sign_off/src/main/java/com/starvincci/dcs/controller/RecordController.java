package com.starvincci.dcs.controller;

import com.alibaba.fastjson.JSONObject;
import com.starvincci.dcs.pojo.record.Record;
import com.starvincci.dcs.pojo.user.UserRole;
import com.starvincci.dcs.pojo.user.UserRoleFile;
import com.starvincci.dcs.service.files.FilesServiceImpl;
import com.starvincci.dcs.service.record.RecordServiceImpl;
import com.starvincci.dcs.service.user.UserRoleFileServiceImpl;
import com.starvincci.dcs.service.user.UserRoleServiceImpl;
import com.starvincci.dcs.service.user.UserServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Resource
    private RecordServiceImpl recordService;
    @Resource
    private FilesServiceImpl filesService;
    @Resource
    private UserRoleServiceImpl userRoleService;
    @Resource
    private UserRoleFileServiceImpl userRoleFileService;
    @Resource
    private UserServiceImpl userService;


    @PostMapping("/add")
    public String addRecord(@RequestBody String strs) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        JSONObject jsonObject = JSONObject.parseObject(strs);
        Record record = new Record();
        record.setApplyName(jsonObject.get("name").toString());
        record.setApplyDate(sf.format(new Date()));
        record.setApplyContent(jsonObject.get("content").toString());
        record.setApplyStatus(1);
        record.setApplyPassDate(null);
        record.setOperator(null);
        record.setInForce(null);
        record.setUserId(Integer.parseInt(jsonObject.get("userId").toString()));
        record.setFileId(Integer.parseInt(jsonObject.get("fileId").toString()));
        System.out.println(record.toString());

        Record resRecord =new Record();
        if ("更新".equals(jsonObject.get("content").toString())) {
            Record record1 = recordService.findRecordOnlyApply(record);
            if (record1 != null) {
                return "该文件已有用户提交更新申请";
            }
            Record record2 = recordService.findRecordOnlyPass(record);
            if(record2!=null){
                return "无法申请，存在已通过审核但并未更新文件的记录";
            }
        }else{
            //        判断当前文件只能有一个用户申请更新，不能重复申请
             resRecord = recordService.findRecordOnly(record);
             System.out.println(resRecord);
            if (resRecord != null) {
                return "请勿重复申请";
            }
        }


        int res = recordService.addRecord(record);
        if (res == 1) {
            return "申请已提交";
        }


        return "申请提交失败";
    }

    @GetMapping("/getRecordStatusOne")
    public List<Record> getRecordStatusOne() {
        List<Record> resList = recordService.getRecordStatusOne();
        for (Record record : resList) {
            record.setFiles(filesService.getFileByFileId(record.getFileId()));
            record.setUsers(userService.getUserById(record.getUserId()));
        }
        return resList;
    }

    @GetMapping("/getRecordByStatus")
    public List<Record> getRecordByStatus(@RequestParam("status") Integer stauts, @RequestParam("userId") Integer userId) {
        List<Record> resList = recordService.getRecordByStatus(stauts, userId);
        for (Record record : resList) {
            record.setFiles(filesService.getFileByFileId(record.getFileId()));

        }
        return resList;
    }

    @PostMapping("/updateRecord")
    public String updateRecord(@RequestBody String strs) {

        System.out.println(strs);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        JSONObject jsonObject = JSONObject.parseObject(strs);
        int status = Integer.parseInt(jsonObject.getString("status"));
        Record newRecord = new Record();
        newRecord.setOperator(SecurityContextHolder.getContext().getAuthentication().getName());
        newRecord.setApplyPassDate(sf.format(new Date()));
        newRecord.setApplyStatus(status);
        newRecord.setId(Integer.parseInt(jsonObject.getString("id")));
        System.out.println(newRecord.toString());
//       通过 添加权限
        if (status == 2) {
            List<UserRole> userRoleList = userRoleService.getAllUserRole(jsonObject.getInteger("userId"));
            for (UserRole userRole : userRoleList) {
                if (userRole.getRoles().getRoleName().equals(jsonObject.getString("applyContent"))) {


                    UserRoleFile userRoleFile = new UserRoleFile();
                    userRoleFile.setUserRoleId(userRole.getId());
                    userRoleFile.setFileId(jsonObject.getInteger("fileId"));
                    userRoleFile.setInForce(null);

                    UserRoleFile userRoleFileOnly = userRoleFileService.getUserRoleFileOnly(userRoleFile);
                    if (userRoleFileOnly == null) {
                        int res = userRoleFileService.addUserRoleFile(userRoleFile);
                        if (res == 1) {
                            System.out.println("添加权限");
                        }
                    }
                }
            }
        }
        Record record2=new Record();
        record2.setFileId(jsonObject.getInteger("fileId"));
        record2.setApplyContent(jsonObject.getString("applyContent"));

        Record record=recordService.findRecordOnlyPass(record2);
            if (record!=null){
                return "审核失败,存在已通过审核但并未更新文件的记录";
            }

        int res = recordService.updateRecord(newRecord);
        if (res == 1) {
            return "操作成功";
        }


        return "操作失败";
    }

}
