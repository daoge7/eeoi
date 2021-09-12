package cn.ccsit.component.fileUpload.config;

import cn.ccsit.common.utils.SpringContextUtils;
import cn.ccsit.component.fileUpload.FileUpload;
import cn.ccsit.component.fileUpload.config.properties.UploadProjectProperties;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class UploadFileConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        UploadProjectProperties properties = SpringContextUtils.getBean(UploadProjectProperties.class);
        
        //todo by lfj 
      //  registry.addResourceHandler(properties.getStaticPath()).addResourceLocations("file:" + FileUpload.getUploadPath());
    }
}
