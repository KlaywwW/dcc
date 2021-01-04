package com.starvincci.dcs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        PrintWriter out = httpServletResponse.getWriter();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 777);
        map.put("message", "Don't login");
        out.write(new ObjectMapper().writeValueAsString(map));
        out.flush();
        out.close();
    }
}
