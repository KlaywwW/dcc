package com.starvincci.dcs;

import com.starvincci.dcs.pojo.files.Files;
import com.starvincci.dcs.service.files.FilesServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class DcsSignOffApplicationTests {

	@Resource
	private FilesServiceImpl filesService;

	@Test
	void contextLoads() {

		String strDate="2020-11-12 18:48:37";

		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date=sf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//获取东八区时间
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
//		System.out.println(c.getTime());
		c.setTime(date);
		//获取年
		int year = c.get(Calendar.YEAR);
		//获取月份，会少一天。所以+1
		int month = c.get(Calendar.MONTH) + 1;
		//获取当前天数
		int day = c.get(Calendar.DAY_OF_MONTH);
		//获取当前小时
		int t = c.get(Calendar.HOUR_OF_DAY);
		//获取当前分钟
		int m = c.get(Calendar.MINUTE);
		//获取当前秒
		int se = c.get(Calendar.SECOND);

		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//格式化时间格式
		String Now = s.format(c.getTime());
		//获得当前日期     format（Obj）：格式化一个对象以生成一个字符串。
		System.out.println("输入的时间:" + year + "-" + month + "-"+ day + " "+t + ":" + m +":" + se);
		System.out.println("当前时间:" +year()+"-"+month()+"-"+day());
		System.out.println(year==year()&&month==month()&&day==day());
		System.out.println("当前日期Now:" + Now);


	}

	public int year(){
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		c.getTime();
		return c.get(Calendar.YEAR);
	}
	public int month(){
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		c.getTime();
		return c.get(Calendar.MONTH)+1;
	}
	public int day(){
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		c.getTime();
		return c.get(Calendar.DAY_OF_MONTH);
	}

}
