package com.starvincci.dcs.service.record;

import com.starvincci.dcs.dao.record.RecordMapper;
import com.starvincci.dcs.pojo.record.Record;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RecordServiceImpl implements RecordService {



    @Resource
    private RecordMapper recordMapper;

    @Override
    public int addRecord(Record record) {
        return recordMapper.addRecord(record);
    }

    @Override
    public List<Record> getRecordStatusOne() {
        return recordMapper.getRecordStatusOne();
    }

    @Override
    public List<Record> getRecordByStatus(Integer stauts,Integer userId) {
        return recordMapper.getRecordByStatus(stauts,userId);
    }

    @Override
    public Record findRecordOnly(Record record) {
        return recordMapper.findRecordOnly(record);
    }

    @Override
    public int delRecord(Integer id) {
        return recordMapper.delRecord(id);
    }

    @Override
    public int updateRecord(Record record) {
        return recordMapper.updateRecord(record);
    }

    @Override
    public Record findRecordOnlyApply(Record record) {
        return recordMapper.findRecordOnlyApply(record);
    }

    @Override
    public Record findRecordOnlyPass(Record record) {
        return recordMapper.findRecordOnlyPass(record);
    }

    @Override
    public int updateRecordOut(Record record) {
        return recordMapper.updateRecordOut(record);
    }

    @Override
    public Record selectPass(Record record) {
        return recordMapper.selectPass(record);
    }
}
