package com.shop.project.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CORSConfiguration implements WebMvcConfigurer {
	
	 @Override
	 public void addCorsMappings(CorsRegistry corsRegistry) {
		 corsRegistry.addMapping("/**").allowCredentials(true).allowedOriginPatterns("*");
	 }

}
