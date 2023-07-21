package com.example.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author BAO 7/3/2023
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    exposeDirectory("resources", registry);
  }

  private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
    Path uploadDir = Paths.get(dirName);
    String uploadPath = uploadDir.toFile().getAbsolutePath();

    if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

    // pathPatterns sẽ thay thế cho dirName
    registry
        .addResourceHandler("/" + dirName + "/**")
        .addResourceLocations("file:/" + uploadPath + "/");
  }

  /**
   * allowCredentials cho phép chia sẽ các cookie cho các origin  được xác định phía trước
   *
   * Khi FE gửi cookie đến BE thì BE phải tra lại cookie cho FE để duy trì đăng nhập allowCredentials(true), tra lại cookie cho FE với origin khác
   * @param registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**").allowedOrigins("http://localhost:3000").allowedMethods("*").allowedHeaders("*").allowCredentials(true);
  }


}
