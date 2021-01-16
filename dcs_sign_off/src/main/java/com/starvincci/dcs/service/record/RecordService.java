package com.starvincci.dcs.service.record;

import com.starvincci.dcs.pojo.record.Record;

import java.util.List;

public interface RecordService {
    int addRecord(Record record);
    List<Record> getRecordStatusOne();
    List<Record> getRecordByStatus(Integer stauts,Integer userId);
    Record findRecordOnly(Record record);
    int delRecord(Integer id);
    int updateRecord(Record record);
    Record findRecordOnlyApply(Record record);
    Record findRecordOnlyPass(Record record);
    int updateRecordOut(Record record);
    Record selectPass(Record record);
}
