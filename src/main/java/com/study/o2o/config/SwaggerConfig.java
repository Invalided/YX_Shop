package com.study.o2o.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 勿忘初心
 * @since 2022-08-15-10:28
 * Swagger2 API文档生成
 *
 * @EnableSwagger2：开启Swagger2功能
 * @Configuration：配置类
 * @EnableWebMvc：用于导入springmvc配置
 * @ComponentScan：扫描control所在的package请修改为你control所在package
 */

@EnableSwagger2
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.study.o2o.web")
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("悦享校园API文档")
                .description("悦享校园项目接口测试")
                .version("1.0.0")
                .termsOfServiceUrl("")
                .contact(new Contact("勿忘初心", "https://github.com/Invalided","ifwi@qq.com" ))
                .license("")
                .licenseUrl("")
                .build();
    }
}
