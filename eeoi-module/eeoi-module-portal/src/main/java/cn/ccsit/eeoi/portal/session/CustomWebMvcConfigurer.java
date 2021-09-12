package cn.ccsit.eeoi.portal.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

	public CustomWebMvcConfigurer() {

	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionHandleInterceptor()).order(1)
		.addPathPatterns("/**")
		.excludePathPatterns("/login")
		.excludePathPatterns("/logout")
		.excludePathPatterns("/")
		.excludePathPatterns("/favicon.ico")
		.excludePathPatterns("/js/**")
		.excludePathPatterns("/login.html");
	}
}
