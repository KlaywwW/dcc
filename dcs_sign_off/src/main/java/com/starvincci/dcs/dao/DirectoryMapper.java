package com.starvincci.dcs.dao;

import com.starvincci.dcs.pojo.Directory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DirectoryMapper {

    /**
     * 查询所有认证项目
     * @return
     */
    List<Directory> getAllDir();

    /**
     * 根据id查询认证项目
     * @param id
     * @return
     */
    Directory getDirById(Integer id);

}
