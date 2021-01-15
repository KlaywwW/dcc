package com.starvincci.dcs.pojo.menu;

import com.starvincci.dcs.pojo.user.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserMenu {

    private Integer id;
    private Integer mid;
    private Integer uid;
    private  Menu menu;
//    private Users users;

}
