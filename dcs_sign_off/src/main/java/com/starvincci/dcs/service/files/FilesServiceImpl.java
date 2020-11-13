package com.starvincci.dcs.service.files;

import com.starvincci.dcs.dao.files.FilesMapper;
import com.starvincci.dcs.pojo.files.Files;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FilesServiceImpl implements FilesService {

    @Resource
    private FilesMapper filesMapper;

    @Override
    public List<Files> getAllFilesByDepIdAndDir(Integer departmentId,Integer dirId) {
        return filesMapper.getAllFilesByDepIdAndDir(departmentId,dirId);
    }

    @Override
    public List<Files> getAllFiles() {
        return filesMapper.getAllFiles();
    }

    @Override
    public Files getFileByFileId(Integer fileId) {
        return filesMapper.getFileByFileId(fileId);
    }

    @Override
    public int addFile(Files files) {
        return filesMapper.addFile(files);
    }

    @Override
    public int updateFilesVersionId(Files files) {
        return filesMapper.updateFilesVersionId(files);
    }


    @Override
    public Files fileIsExist(String fileName) {
        return filesMapper.fileIsExist(fileName);
    }

    @Override
    public Files getFilesTop() {
        return filesMapper.getFilesTop();
    }

    @Override
    public List<Files> getAllFilesByDir(Integer dirId) {
        return filesMapper.getAllFilesByDir(dirId);
    }

    @Override
    public List<Files> getFilesVersion(Integer originalFileId) {
        return filesMapper.getFilesVersion(originalFileId);
    }
}
