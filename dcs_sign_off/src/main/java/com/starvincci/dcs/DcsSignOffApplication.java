package com.starvincci.dcs;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//开启定时任务
public class DcsSignOffApplication {


	public static void main(String[] args) {
		SpringApplication.run(DcsSignOffApplication.class, args);
	}
	@Bean
	public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
		ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
		// IP白名单 (没有配置或者为空，则允许所有访问)
		registrationBean.addInitParameter("allow", "");
		// IP黑名单 (存在共同时，deny优先于allow)
		registrationBean.addInitParameter("deny", "");
		registrationBean.addInitParameter("loginUsername", "root");
		registrationBean.addInitParameter("loginPassword", "123");
		registrationBean.addInitParameter("resetEnable", "false");
		return registrationBean;
	}

}
