package com.starvincci.dcs.dao.files;

import com.starvincci.dcs.pojo.files.Files;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FilesMapper {

    /**
     * 查询对应目录下对应部门的所有文件
     */
    List<Files> getAllFilesByDepIdAndDir(@Param("departmentId") Integer departmentId,@Param("dirId") Integer dirId);

    /**
     * 查询所有文件
     * @return
     */
    List<Files> getAllFiles();

    /**
     * 根据id查找文件
     * @return
     */
    Files getFileByFileId(Integer fileId);

    /**
     * 添加一条文件上传记录
     * @param files 文件
     * @return
     */
    int addFile(Files files);

    /**
     * 查找是否有同名的文件
     * @param fileName
     * @return
     */
    Files fileIsExist(String fileName);

    /**
     * 修改最新版本号
     * @param files
     * @return
     */
    int updateFilesVersionId(Files files);

    /**
     * 查询最新的文件id
     * @return
     */
    Files getFilesTop();

    /**
     * 根据目录id查询所有文件
     * @param dirId
     * @return
     */
    List<Files> getAllFilesByDir(@Param("dirId") Integer dirId);

    /**
     * 查询历史文件版本
     * @param originalFileId
     * @return
     */
    List<Files> getFilesVersion(@Param("originalFileId") Integer originalFileId);

}
