package hirain.itd.hmi.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;


import javax.servlet.MultipartConfigElement;



@Configuration
public class UploadFileConfig {

    @Value("${web.upload-path}")
    private String uploadFolder;

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadFolder);
        //文件最大KB,MB
        factory.setMaxFileSize(DataSize.parse("100MB"));
        factory.setMaxRequestSize(DataSize.parse("100MB"));
        return factory.createMultipartConfig();
    }

}
