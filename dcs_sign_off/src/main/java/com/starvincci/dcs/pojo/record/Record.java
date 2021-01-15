package com.starvincci.dcs.pojo.record;

import com.starvincci.dcs.pojo.files.Files;
import com.starvincci.dcs.pojo.user.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private Integer id;
    private String applyName;
    private String applyDate;
    private String applyContent;
    private Integer applyStatus;//1.待通过，2.通过，3.未通过
    private String applyPassDate;
    private String operator;
    private Integer inForce;
    private Integer userId;
    private Integer fileId;
    private Files files;
    private Users users;


}
