package com.starvincci.dcs.dao.record;

import com.starvincci.dcs.pojo.record.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {

    /**
     * 添加日志记录
     * @param log
     * @return
     */
    int  addLog(Log log);

    /**
     * 根据用户查询操作日志
     * @param userId
     * @return
     */
    List<Log> getLogListByUser(Integer userId);


}
