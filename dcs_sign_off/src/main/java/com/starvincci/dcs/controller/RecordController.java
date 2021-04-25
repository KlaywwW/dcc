package com.starvincci.dcs.controller;

import com.alibaba.fastjson.JSONObject;
import com.starvincci.dcs.pojo.RespBean;
import com.starvincci.dcs.pojo.record.Record;
import com.starvincci.dcs.pojo.user.UserRoleFile;
import com.starvincci.dcs.service.files.FilesServiceImpl;
import com.starvincci.dcs.service.record.RecordServiceImpl;
import com.starvincci.dcs.service.user.UserRoleFileServiceImpl;
//import com.starvincci.dcs.service.user.UserRoleServiceImpl;
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

    /**
     * 上传
     */
    private String UPLOAD = "upload";
    /**
     * 下载
     */
    private String DOWNLOAD = "download";
    /**
     * 查看
     */
    private String LOOK = "look";
    /**
     * 修改
     */
    private String UPDATE = "update";

    @Resource
    private RecordServiceImpl recordService;
    @Resource
    private FilesServiceImpl filesService;
    @Resource
    private UserRoleFileServiceImpl userRoleFileService;
    @Resource
    private UserServiceImpl userService;


    /**
     * 提交申请
     *
     * @param strs
     * @return
     */
    @PostMapping("/add")
    public RespBean addRecord(@RequestBody String strs) {
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

        Record resRecord = new Record();
        if ("更新".equals(jsonObject.get("content").toString())) {
            Record record1 = recordService.findRecordOnlyApply(record);
            if (record1 != null) {
                return RespBean.error("error", "该文件已有用户提交更新申请");
            }
            Record record2 = recordService.findRecordOnlyPass(record);
            if (record2 != null) {
                return RespBean.error("error", "无法申请，存在已通过审核但并未更新文件的记录");
            }
        } else {
            //        判断当前文件只能有一个用户申请更新，不能重复申请
            resRecord = recordService.findRecordOnly(record);
            if (resRecord != null) {
                return RespBean.error("error", "请勿重复申请");
            }
            Record record1 = recordService.selectPass(record);
            if (record1 != null) {
                return RespBean.error("error", "已提交过此申请并已通过,请刷新页面或重新登录");
            }
        }


        int res = recordService.addRecord(record);
        if (res == 1) {
            return RespBean.ok("ok", "申请已提交");
        }


        return RespBean.error("error", "申请提交失败");
    }

    /**
     * 获取申请状态为待审核的信息
     */
    @GetMapping("/getRecordStatusOne")
    public List<Record> getRecordStatusOne() {
        List<Record> resList = recordService.getRecordStatusOne();
        for (Record record : resList) {
            record.setFiles(filesService.getFileByFileId(record.getFileId()));
            record.setUsers(userService.getUserById(record.getUserId()));
        }
        return resList;
    }

    /**
     * 根据不同状态获取信息
     * @param stauts
     * @param userId
     * @return
     */
    @GetMapping("/getRecordByStatus")
    public List<Record> getRecordByStatus(@RequestParam("status") Integer stauts, @RequestParam("userId") Integer userId) {
        List<Record> resList = recordService.getRecordByStatus(stauts, userId);
        for (Record record : resList) {
            record.setFiles(filesService.getFileByFileId(record.getFileId()));

        }
        return resList;
    }

    /**
     * 修改申请信息
     * @param strs
     * @return
     */
    @PostMapping("/updateRecord")
    public RespBean updateRecord(@RequestBody String strs) {


        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        JSONObject jsonObject = JSONObject.parseObject(strs);
        int status = Integer.parseInt(jsonObject.getString("status"));
        Record newRecord = new Record();
        newRecord.setOperator(SecurityContextHolder.getContext().getAuthentication().getName());
        newRecord.setApplyPassDate(sf.format(new Date()));
        newRecord.setApplyStatus(status);
        newRecord.setId(Integer.parseInt(jsonObject.getString("id")));
//       通过 添加权限
        String look = "浏览";
        String download = "下载";
        String upload = "上传";
        String update = "更新";
        if (status == 2) {
            UserRoleFile userRoleFile = new UserRoleFile();
            userRoleFile.setUserId(jsonObject.getInteger("userId"));
            userRoleFile.setFileId(jsonObject.getInteger("fileId"));
            String applyContent = jsonObject.getString("applyContent");
            if (look.equals(applyContent)) {
                userRoleFile.setOperation(LOOK);
            } else if (download.equals(applyContent)) {
                userRoleFile.setOperation(DOWNLOAD);
            } else if (upload.equals(applyContent)) {
                userRoleFile.setOperation(UPLOAD);
            } else if (update.equals(applyContent)) {
                userRoleFile.setOperation(UPDATE);
            }
//
            UserRoleFile userRoleFileOnly = userRoleFileService.getUserRoleFileOnly(userRoleFile);
            if (userRoleFileOnly == null) {
//                添加权限
                System.err.println(userRoleFile.toString());
                userRoleFileService.addUserRoleFile(userRoleFile);
            }
        }
//        不通过只需修改状态，不用添加权限
        Record record2 = new Record();
        record2.setFileId(jsonObject.getInteger("fileId"));
        record2.setApplyContent(jsonObject.getString("applyContent"));
        Record record = recordService.findRecordOnlyPass(record2);
        if (record != null && record.getApplyContent().equals(update)) {
            return RespBean.error("error", "审核失败,存在已通过审核但并未更新文件的记录");
        }
        int res = recordService.updateRecord(newRecord);
        if (res == 1) {
            return RespBean.ok("ok", "操作成功");
        }
        return RespBean.error("error", "操作失败");
    }

}
