package com.starvincci.dcs;

import com.starvincci.dcs.dao.user.RoleMapper;
import com.starvincci.dcs.pojo.files.Files;
import com.starvincci.dcs.service.files.FilesServiceImpl;
import com.starvincci.dcs.service.menu.MenuServiceImpl;
import com.starvincci.dcs.service.user.UserRoleFileServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class DcsSignOffApplicationTests {

	@Resource
	private UserRoleFileServiceImpl userRoleFileService;


	@Test
	void test(){
		userRoleFileService.getAllByUserId(4).stream().forEach(System.out::println);
	}

}
