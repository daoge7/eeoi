package cn.ccsit.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class LocaleResolverConfig implements WebMvcConfigurer {

	/**
	 * 国际化，设置默认的语言为中文 将用户的区域信息存在session 也可以存在cookie,由客户端保存
	 * {@link org.springframework.web.servlet.i18n.CookieLocaleResolver}
	 *
	 * @return
	 */

	@Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}
	/**
	 * 国际化，设置url识别参数
	 *
	 * @return
	 */
	/* @Bean 
	 *
	 * public LocaleChangeInterceptor localeChangeInterceptor() {
	 * LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	 * lci.setParamName("lang"); return lci; }
	 */
	  
	  @Override 
	  public void addInterceptors(InterceptorRegistry registry) {
		  //registry.addInterceptor(registry); 
	  }
	 

}
