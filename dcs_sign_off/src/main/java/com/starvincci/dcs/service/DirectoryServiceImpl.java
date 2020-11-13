package com.starvincci.dcs.service;

import com.starvincci.dcs.dao.DirectoryMapper;
import com.starvincci.dcs.pojo.Directory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Resource
    private DirectoryMapper directoryMapper;

    @Override
    public List<Directory> getAllDir() {
        return directoryMapper.getAllDir();
    }

    @Override
    public Directory getDirById(Integer id) {
        return directoryMapper.getDirById(id);
    }
}
