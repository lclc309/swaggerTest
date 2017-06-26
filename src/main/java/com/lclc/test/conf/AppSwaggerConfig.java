package com.lclc.test.conf;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lclc.test.core.ResponseResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger的配置项
 *
 * @author lichao 2017年1月4日
 */
@Configuration
@EnableSwagger2
public class AppSwaggerConfig {

    @Bean
    public Docket api() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2).select() // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build().apiInfo(apiInfo());

        // 新增错误返回值
        docket = docket.useDefaultResponseMessages(false)// 去除默认的选择 ;
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages());

        return docket;
    }

    /**
     * 获取普通code的返回值
     * 
     * @return
     */
    private List<ResponseMessage> responseMessages() {

        return Lists.newArrayList(
                new ResponseMessageBuilder().code(500)
                        .message(JSON.toJSONString(ResponseResult.error500().message("systemerror"))).build(),
                new ResponseMessageBuilder().code(403).message(JSON.toJSONString(ResponseResult.error403())).build());
    }

    /**
     * 获取基本信息
     * 
     * @return
     */
    private ApiInfo apiInfo() {

        ApiInfo apiInfo = new ApiInfoBuilder().title("李超测试项目").description("基于猫趣电商社区平台架构,实现resetful风格的统一化接口模式")
                .termsOfServiceUrl("http://www.cnblogs.com/lic309/").contact("大招无限").version("1.0").build();
        return apiInfo;
    }

}
