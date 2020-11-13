package com.starvincci.dcs.controller;

import com.starvincci.dcs.pojo.files.Files;
import com.starvincci.dcs.pojo.record.Log;
import com.starvincci.dcs.pojo.record.Record;
import com.starvincci.dcs.service.files.FilesServiceImpl;
import com.starvincci.dcs.service.log.LogServiceImpl;
import com.starvincci.dcs.service.record.RecordServiceImpl;
import com.starvincci.dcs.service.user.UserServiceImpl;
import com.starvincci.dcs.utils.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FilesController {

    @Resource
    private FilesServiceImpl filesService;
    @Resource
    private RecordServiceImpl recordService;
    @Resource
    private UserServiceImpl userService;
    @Resource
    private LogServiceImpl logService;


    private FileUtil fileUtil = new FileUtil();

    @PostMapping("/upload")
    public HashMap<String, Object> uploadFiles(@RequestParam("files") List<MultipartFile> listFile,
                                               @RequestParam("userName") String userName,
                                               @RequestParam("departmentId") int departmentId,
                                               @RequestParam("dirId") Integer dirId,
                                               @RequestParam("userId") Integer userId) {
//
//        List<MultipartFile> listFile=
        HashMap<String, Object> hashMap = new HashMap<>();
        String filePath = getFilePath(dirId,departmentId);


        if (fileUtil.upload(listFile, filePath, filesService)) {
            System.out.println("------上传成功");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Files files = new Files();
            files.setCdate(sf.format(new Date()));
            files.setCuser(userName);
            files.setFilesName(listFile.get(0).getOriginalFilename());
            files.setFilesPath(filePath + "\\" + listFile.get(0).getOriginalFilename());
            files.setDepartmentId(departmentId);
            files.setIsoNo(listFile.get(0).getOriginalFilename().substring(0,listFile.get(0).getOriginalFilename().indexOf('.')));
            files.setVersion(0);
            files.setOriginalFileId(null);
            files.setVersionNo(1);
            files.setDirId(dirId);
            files.setUserId(userId);
            System.out.println(files.toString());
            int res = filesService.addFile(files);
            if (res > 0) {

//                日志记录
                recordLog(2,files,userId);


                hashMap.put("msg", "上传成功");
                return hashMap;
            }
            hashMap.put("msg", "上传失败");
        } else {
            hashMap.put("msg", "文件同名,上传失败!");
        }

        return hashMap;
    }

    @GetMapping("/show")
    public HashMap<String, Object> show(@RequestParam("departmentId") Integer departmentId,
                                        @RequestParam("dirId") Integer dirId) {
        HashMap<String, Object> map = new HashMap<>();

        List<Files> filesList = filesService.getAllFilesByDepIdAndDir(departmentId,dirId);

        List<Files> filesData = new ArrayList<>();
        List<Files> filesVersion = new ArrayList<>();
        List<Files> filesResult = new ArrayList<>();
        for (Files files : filesList) {
            if (files.getVersion() != 0 && files.getVersionNo() == 1) {
                filesVersion.add(filesService.getFileByFileId(files.getVersion()));
            }
            if (files.getVersion() == 0 && files.getVersionNo() == 1) {
                filesData.add(files);
            }
        }

        for (Files files : filesData) {
            files.setDepartment(userService.getDepById(files.getDepartmentId()));
            filesResult.add(files);
        }
        for (Files files : filesVersion) {
            files.setDepartment(userService.getDepById(files.getDepartmentId()));
            filesResult.add(files);
        }

        map.put("data", filesResult);
        return map;
    }
    @GetMapping("/showAllByDir")
    public HashMap<String, Object> showAllByDir(@RequestParam("dirId") Integer dirId) {
        HashMap<String, Object> map = new HashMap<>();

        List<Files> filesList = filesService.getAllFilesByDir(dirId);

        List<Files> filesData = new ArrayList<>();
        List<Files> filesVersion = new ArrayList<>();
        List<Files> filesResult = new ArrayList<>();
        for (Files files : filesList) {
            if (files.getVersion() != 0 && files.getVersionNo() == 1) {
                filesVersion.add(filesService.getFileByFileId(files.getVersion()));
            }
            if (files.getVersion() == 0 && files.getVersionNo() == 1) {
                filesData.add(files);
            }
        }

        for (Files files : filesData) {
            files.setDepartment(userService.getDepById(files.getDepartmentId()));
            filesResult.add(files);
        }
        for (Files files : filesVersion) {
            files.setDepartment(userService.getDepById(files.getDepartmentId()));
            filesResult.add(files);
        }

        map.put("data", filesResult);
        return map;
    }
    @GetMapping("/getAllDep")
    public HashMap<String, Object> getAllDep(){
        HashMap<String, Object> map = new HashMap<>();

        map.put("data", userService.getAllDep());
        return map;
    }

    @GetMapping("/showFileList")
    public HashMap<String, Object> showFileList() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("data", filesService.getAllFiles());
        return map;
    }

    @GetMapping("/downloadFile")
    public void downLoadFile(@RequestParam("fileId") Integer fileId,
                             @RequestParam("userId") Integer userId,
                             HttpServletResponse response,
                             HttpServletRequest request) {
        Files files = filesService.getFileByFileId(fileId);

        try {
            fileUtil.download(files.getFilesPath(), files.getFilesName(), response, request);
            //                日志记录
            recordLog(3,files,userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/update")
    public HashMap<String, Object> updateVersion(@RequestParam("file") List<MultipartFile> listFile,
                                                 @RequestParam("fileId") Integer fileId,
                                                 @RequestParam("userName") String userName,
                                                 @RequestParam("departmentId") Integer departmentId,
                                                 @RequestParam("dirId") Integer dirId,
                                                 @RequestParam("userId") Integer userId) {
        String filePath = getFilePath(dirId,departmentId);
        HashMap<String, Object> hashMap = new HashMap<>();
        String cuser=userName;
        Record record = new Record();
        record.setApplyContent("更新");
        record.setFileId(fileId);
        //        更新时判断待审核更新 中为审核的文件提示。
        Record record1=recordService.findRecordOnlyApply(record);
        if (record1!=null){
            hashMap.put("msg", "更新失败!该文件有更新申请，请先审核");
            return hashMap;
        }

        Record recordResult=recordService.findRecordOnlyPass(record);
        System.out.println(recordResult!=null);
        if (recordResult!=null){
            cuser=recordResult.getApplyName();
        }


        Files files = filesService.getFileByFileId(fileId);

        if (fileUtil.upload(listFile, filePath, filesService)) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Files newFiles = new Files();
            newFiles.setFilesPath(filePath + "\\" + listFile.get(0).getOriginalFilename());
            newFiles.setFilesName(listFile.get(0).getOriginalFilename());
            newFiles.setCuser(cuser);
            newFiles.setCdate(sf.format(new Date()));
            newFiles.setDepartmentId(departmentId);
            newFiles.setIsoNo(listFile.get(0).getOriginalFilename().substring(0,listFile.get(0).getOriginalFilename().indexOf('.')));
            newFiles.setVersion(0);
            newFiles.setVersionNo(files.getVersionNo() + 1);
            newFiles.setDirId(dirId);
            newFiles.setUserId(userId);

            if (files.getOriginalFileId() == null || "null".equals(files.getOriginalFileId())) {
                newFiles.setOriginalFileId(fileId);
            } else {
                newFiles.setOriginalFileId(files.getOriginalFileId());
            }

            int res = filesService.addFile(newFiles);


            Files topFiles = filesService.getFilesTop();
            if (res > 0) {
                Files updateFiles = new Files();
                updateFiles.setFilesId(topFiles.getOriginalFileId());
                updateFiles.setVersion(topFiles.getFilesId());
                filesService.updateFilesVersionId(updateFiles);
                // 修改状态
                if (recordResult!=null) {
                    recordService.updateRecordOut(recordResult);
                }
                recordLog(5,newFiles,userId);
                hashMap.put("msg", "更新成功");
                return hashMap;
            }
            hashMap.put("msg", "更新失败");
        } else {
            hashMap.put("msg", "文件同名,更新失败!");
        }

        return hashMap;
    }

    @GetMapping("/getFilesVersion")
    public HashMap<String, Object> getFilesVersion(@RequestParam("original") Integer originalFileId){

        HashMap<String, Object> hashMap=new HashMap<>();
        List<Files> resData=new ArrayList<>();
        for (Files files:filesService.getFilesVersion(originalFileId)) {
            resData.add(filesService.getFileByFileId(originalFileId));
            resData.add(files);
        }

        hashMap.put("data",resData);
        return hashMap;
    }
    @GetMapping("/showPdfRecord")
    private void showPdfRecord(@RequestParam("fileId")Integer fileId,@RequestParam("userId") Integer userId){
        Files files=filesService.getFileByFileId(fileId);
        recordLog(5,files,userId);
    }

    @GetMapping("/showLog")
    private List<Log> showLog(@RequestParam("userId") Integer userId){
        List<Log> logList=logService.getLogListByUser(userId);
        for (Log log:logList) {
            log.setUsers(userService.getUserById(userId));
        }
        return logList;
    }


    private String getFilePath(Integer dirId,Integer departmentId) {
        File file = new File("D:\\dcs\\");
        String filePath=file.getAbsolutePath()
                +"\\"+
                userService.getDirById(dirId).getDirName()
                +"\\"+
                userService.getDepById(departmentId).getDepartmentName();

        return filePath;
    }

    /**
     * 记录用户基本操作日志
     * @param operation  2.上传 3.下载 4.查看 5.更新
     * @param files
     * @param userId
     */
    private void recordLog(Integer operation,Files files,Integer userId){
        SimpleDateFormat sfLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log log=new Log();
        if (operation==2){
            log.setOperationContent("用户："+userService.getUserById(userId).getAccount()+"上传了-->"+files.getFilesName());
        }else if(operation==3){
            log.setOperationContent("用户："+userService.getUserById(userId).getAccount()+"下载了-->"+files.getFilesName());
        }else if(operation==4){
            log.setOperationContent("用户："+userService.getUserById(userId).getAccount()+"更新了-->"+files.getFilesName());
        }else if(operation==5){
            log.setOperationContent("用户："+userService.getUserById(userId).getAccount()+"浏览了-->"+files.getFilesName());
        }

        log.setOperationDate(sfLog.format(new Date()));
        log.setUserId(userId);
        int resLog=logService.addLog(log);
    }


}
