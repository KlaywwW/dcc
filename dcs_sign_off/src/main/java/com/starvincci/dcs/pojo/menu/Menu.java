package com.starvincci.dcs.pojo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Menu {
    private Integer id;
    private String menuPath; //菜单地址
    private String menuName; //菜单名称

}
