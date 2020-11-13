package com.starvincci.dcs.utils;

import com.starvincci.dcs.service.files.FilesServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
public class FileUtil {
//    @Resource
//   private FilesServiceImpl filesService;

    /**
     * 上传
     * @param files
     * @param filePath 文件父目录
     * @return
     */

    public boolean upload(List<MultipartFile> files, String filePath,FilesServiceImpl filesService) {
//        List<MultipartFile> files = (MultipartHttpServletRequest)request.getFiles("file");

        if(files.isEmpty()){
            return false;
        }

        String path = filePath ;

        for(MultipartFile file:files) {
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);
//            Files fileExist=filesService.fileIsExist(fileName);
            System.out.println(filesService.fileIsExist(fileName));
            if (filesService.fileIsExist(fileName)!=null){
                System.err.println("已存在相同名称文件");
                return false;
            }
            if (file.isEmpty()) {
            } else {
                File dest = new File(path + "/" + fileName);
                //判断文件父目录是否存在
                if(!dest.exists()){
                    // 递归生成文件夹
                    dest.mkdirs();
                }
                try {
                    file.transferTo(dest);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public void download(String filePath, String fileName, HttpServletResponse response, HttpServletRequest request) throws Exception {

        File file = new File(filePath);
        //相应头的处理
        //清空response中的输出流
//        response.reset();
        //设置文件大小
        response.setContentLength((int) file.length());
        //设置Content-Type头
        response.setContentType("application/octet-stream;charset=utf-8");
        //设置Content-Disposition头 以附件形式解析
//        String encodedFilename = processFileName(request, fileName);
        System.err.println(fileName);
        String encodedFilename = processFileName(request, fileName);
        response.addHeader("fileName",encodedFilename);
        response.addHeader("Content-Disposition", "attachment;filename=" + encodedFilename);
        //将来文件流写入response中
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //获取文件输入流
            fileInputStream = new FileInputStream(file);
            //创建数据缓冲区
            byte[] buffers = new byte[1024];
            //通过response中获取ServletOutputStream输出流
            outputStream = response.getOutputStream();
            int length;
            while ((length = fileInputStream.read(buffers)) > 0) {
                //写入到输出流中
                outputStream.write(buffers, 0, length);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //流的关闭
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取URL编码后的原始文件名
     *
     * @param request          ：客户端请求
     * @param fileNames ：原始文件名
     * @return ：
     */
    public static String processFileName(HttpServletRequest request, String fileNames) {
        String codedfilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
                    && -1 != agent.indexOf("Trident")) {// ie

                String name = java.net.URLEncoder.encode(fileNames, "UTF-8");

                codedfilename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等

                codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }
}
