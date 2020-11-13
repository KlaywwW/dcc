package com.starvincci.dcs.service.files;

import com.starvincci.dcs.pojo.files.Files;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilesService {

    List<Files> getAllFilesByDepIdAndDir(@Param("departmentId") Integer departmentId, @Param("dirId") Integer dirId);

    List<Files> getAllFiles();

    Files getFileByFileId(Integer fileId);

    int addFile(Files files);

    Files fileIsExist(String fileName);

    int updateFilesVersionId(Files files);

    Files getFilesTop();

    List<Files> getAllFilesByDir(@Param("dirId") Integer dirId);

    List<Files> getFilesVersion(@Param("originalFileId") Integer originalFileId);
}
