package com.starvincci.dcs.service.log;

import com.starvincci.dcs.pojo.record.Log;

import java.util.List;

public interface LogService {


    int addLog(Log log);
    List<Log> getLogListByUser(Integer userId);

}
