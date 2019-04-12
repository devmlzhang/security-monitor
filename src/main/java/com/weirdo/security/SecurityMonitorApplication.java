package com.weirdo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.util.List;


@SpringBootApplication
@MapperScan("com.ryoma.locationdevice.dao")
public class SecurityMonitorApplication implements WebMvcConfigurer{
    private final  static Logger log= LoggerFactory.getLogger(SecurityMonitorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SecurityMonitorApplication.class, args);
        log.info("服务启动...");
    }

    /**
     * 序列换成json时,将所有的long变成string 因为js中得数字类型不能包含所有的java long值
     */
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
		/*simpleModule.addSerializer(Date.class, CommonDateDeserializer.INSTANCE);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));*/
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }
}
