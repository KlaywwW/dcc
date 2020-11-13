package com.starvincci.dcs.service;

import com.starvincci.dcs.pojo.Directory;

import java.util.List;

public interface DirectoryService {
    List<Directory> getAllDir();

    Directory getDirById(Integer id);
}
