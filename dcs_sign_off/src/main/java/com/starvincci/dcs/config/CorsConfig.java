package com.starvincci.dcs.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options")
                .allowCredentials(false).maxAge(3600);
        super.addCorsMappings(registry);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); //允许任何域名
        corsConfiguration.addAllowedHeader("*"); //允许任何头
        corsConfiguration.addAllowedMethod("*"); //允许任何方法
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); //注册
        return new CorsFilter(source);
    }

    /**
     * 暂时还未找到动态添加映射
     * 只能一个一个文件夹的加了 呜呜呜~
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dir1/pdf1/**").addResourceLocations("file:D://dcs/流程作业指导书/人资管理部/");
        registry.addResourceHandler("/dir1/pdf2/**").addResourceLocations("file:D://dcs/流程作业指导书/财务部/");
        registry.addResourceHandler("/dir1/pdf3/**").addResourceLocations("file:D://dcs/流程作业指导书/营业部/");
        registry.addResourceHandler("/dir1/pdf4/**").addResourceLocations("file:D://dcs/流程作业指导书/生产部/");
        registry.addResourceHandler("/dir1/pdf5/**").addResourceLocations("file:D://dcs/流程作业指导书/品保中心/");
        registry.addResourceHandler("/dir1/pdf6/**").addResourceLocations("file:D://dcs/流程作业指导书/TPM/");

        registry.addResourceHandler("/dir2/pdf1/**").addResourceLocations("file:D://dcs/工序标准作业票/人资管理部/");
        registry.addResourceHandler("/dir2/pdf2/**").addResourceLocations("file:D://dcs/工序标准作业票/财务部/");
        registry.addResourceHandler("/dir2/pdf3/**").addResourceLocations("file:D://dcs/工序标准作业票/营业部/");
        registry.addResourceHandler("/dir2/pdf4/**").addResourceLocations("file:D://dcs/工序标准作业票/生产部/");
        registry.addResourceHandler("/dir2/pdf5/**").addResourceLocations("file:D://dcs/工序标准作业票/品保中心/");
        registry.addResourceHandler("/dir2/pdf6/**").addResourceLocations("file:D://dcs/工序标准作业票/TPM/");

        registry.addResourceHandler("/dir3/pdf1/**").addResourceLocations("file:D://dcs/设备操作规范/人资管理部/");
        registry.addResourceHandler("/dir3/pdf2/**").addResourceLocations("file:D://dcs/设备操作规范/财务部/");
        registry.addResourceHandler("/dir3/pdf3/**").addResourceLocations("file:D://dcs/设备操作规范/营业部/");
        registry.addResourceHandler("/dir3/pdf4/**").addResourceLocations("file:D://dcs/设备操作规范/生产部/");
        registry.addResourceHandler("/dir3/pdf5/**").addResourceLocations("file:D://dcs/设备操作规范/品保中心/");
        registry.addResourceHandler("/dir3/pdf6/**").addResourceLocations("file:D://dcs/设备操作规范/TPM/");

        registry.addResourceHandler("/dir4/pdf1/**").addResourceLocations("file:D://dcs/品管作业指导书/人资管理部/");
        registry.addResourceHandler("/dir4/pdf2/**").addResourceLocations("file:D://dcs/品管作业指导书/财务部/");
        registry.addResourceHandler("/dir4/pdf3/**").addResourceLocations("file:D://dcs/品管作业指导书/营业部/");
        registry.addResourceHandler("/dir4/pdf4/**").addResourceLocations("file:D://dcs/品管作业指导书/生产部/");
        registry.addResourceHandler("/dir4/pdf5/**").addResourceLocations("file:D://dcs/品管作业指导书/品保中心/");
        registry.addResourceHandler("/dir4/pdf6/**").addResourceLocations("file:D://dcs/品管作业指导书/TPM/");
        super.addResourceHandlers(registry);
    }
}
