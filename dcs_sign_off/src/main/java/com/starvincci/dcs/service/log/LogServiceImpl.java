package com.starvincci.dcs.service.log;

import com.starvincci.dcs.dao.record.LogMapper;
import com.starvincci.dcs.pojo.record.Log;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogMapper logMapper;


    @Override
    public int addLog(Log log) {
        return logMapper.addLog(log);
    }

    @Override
    public List<Log> getLogListByUser(Integer userId) {
        return logMapper.getLogListByUser(userId);
    }
}
