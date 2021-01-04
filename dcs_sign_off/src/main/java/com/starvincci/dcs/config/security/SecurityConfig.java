package com.starvincci.dcs.config.security;

//import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starvincci.dcs.config.CustomAuthenticationEntryPoint;
import com.starvincci.dcs.pojo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 1.添加SpringSecurity的依赖
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-security</artifactId>
 * </dependency>
 * 2.编写配置文件 SecurityConfig
 * 1.继承 WebSecurityConfigurerAdapter
 * 2.重写
 * protected void configure(HttpSecurity http)
 * public void configure(AuthenticationManagerBuilder auth)
 * 3.编写MyUserDetailsService 并实现 UserDetailsService 接口
 * <p>
 * 4.编写MyUserDetails 实现 UserDetails
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Autowired
    private MyUserDetailsService myCustomUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
//                .authenticationEntryPoint(new AuthenticationEntryPoint() {
//                    @Override
//                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
//                        resp.setContentType("application/json;charset=utf-8");
//                        PrintWriter out = resp.getWriter();
//                        Map<String, Object> map = new HashMap<String, Object>();
//                        map.put("code", 403);
//                        map.put("message", "未登录");
////                        if (authException instanceof InsufficientAuthenticationException) {
////                            respBean.setMsg("请求失败，请联系管理员!");
////                        }
//                        out.write(new ObjectMapper().writeValueAsString(map));
//                        out.flush();
//                        out.close();
//                    }
//                })
                //未登录时，进行json格式的提示，很喜欢这种写法，不用单独写一个又一个的类
                .authenticationEntryPoint((request,response,authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",403);
                    map.put("message","未登录");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })

                .and()
                .authorizeRequests()
                //不需要验证的路径
                .antMatchers("/druid/**").permitAll()
                .anyRequest().authenticated() //必须授权才能范围
                .and()
                .formLogin() //使用自带的登录
                .permitAll()
                //登录失败，返回json
                .failureHandler((request, response, ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<String, Object>();
                    System.out.println("登录失败!请联系管理员 o(╥﹏╥)o ");
                    map.put("code", 401);
                    if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
                        map.put("message", "用户名或密码错误");
                    } else if (ex instanceof DisabledException) {
                        map.put("message", "账户已禁用");
                    } else {
                        map.put("message", "登录失败!请联系管理员 o(╥﹏╥)o ");
                    }
                    System.out.println(map.get("message"));
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                //登录成功，返回json
                .successHandler((request, response, authentication) -> {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("code", 200);
                    map.put("message", "登录成功");
                    map.put("data", authentication);

                    HttpSession session = request.getSession();
                    session.setAttribute("userInfo", authentication);
                    System.out.println(session);
                    System.out.println("登录成功");
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .exceptionHandling()
                //没有权限，返回json
                .accessDeniedHandler((request, response, ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("code", 403);
                    map.put("message", "权限不足");
                    System.out.println("权限不足");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .logout()
                //退出成功，返回json
                .logoutSuccessHandler((request, response, authentication) -> {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("code", 200);
                    map.put("message", "退出成功");
                    System.out.println("退出登录");
                    map.put("data", authentication);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .permitAll();
//        http.sessionManagement().
        //不需要验证路径
//        http.authorizeRequests().antMatchers("/druid/**").permitAll();
        //开启跨域访问
        http.cors().disable();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myCustomUserService).passwordEncoder(myPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        //对于在header里面增加token等类似情况，放行所有OPTIONS请求。
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
