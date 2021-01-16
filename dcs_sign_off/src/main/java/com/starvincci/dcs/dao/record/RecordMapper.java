package com.starvincci.dcs.dao.record;

import com.starvincci.dcs.pojo.record.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordMapper {
    /**
     * 添加一条数据
     * @param record
     * @return
     */
    int addRecord(Record record);

    /**
     * 获取所有待审核的申请单
     * @return
     */
    List<Record> getRecordStatusOne();

    /**
     * 根据申请状态显示
     * @param stauts userId
     * @return
     */
    List<Record> getRecordByStatus(@Param("stauts") Integer stauts,@Param("userId") Integer userId);

    /**
     * 判断不能重复申请
     * @param record 判断条件文件id，申请用户id，申请内容，申请状态为1
     * @return
     */
    Record findRecordOnly(Record record);

    /**
     * 判断申请更新所有用户只能有个一用户申请
     * @param record 判断条件文件id，申请内容，申请状态为1
     * @return
     */
    Record findRecordOnlyApply(Record record);
    /**
     * 更新审核通过但还未更新的，无法进行审核
     * @param record 判断条件文件id，申请内容，申请状态为2
     * @return
     */
    Record findRecordOnlyPass(Record record);

    /**
     * 删除一条申请数据
     * @param id
     * @return
     */
    int delRecord(Integer id);

    /**
     * 审核通过或不通过修改状态
     * @param record 记录id 操作人员 修改时间
     * @return
     */
    int updateRecord(Record record);

    /**
     * 将文件已更新的更新申请记录的修改为失效
     * @param record 主键id
     * @return
     */
    int updateRecordOut(Record record);

    /**
     * 申请已通过，不能重复申请
     * @param record
     * @return
     */
    Record selectPass(Record record);


}
