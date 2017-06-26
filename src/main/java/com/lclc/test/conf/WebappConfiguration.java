package com.lclc.test.conf;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.lclc.test.conf.jackson.MyBeanSerializerModifier;
import com.lclc.test.util.Tools;

@Configuration
@ConditionalOnWebApplication
// @EnableTransactionManagement
public class WebappConfiguration {

    private static Logger LOGGER = LoggerFactory.getLogger(WebappConfiguration.class);

    @Autowired
    private AppConfig appConfig;

    // @Bean
    // // 因spring boot升级造成乱码 所以注释掉手动设置编码
    // public CharacterEncodingFilter characterEncodingFilter() {
    // final CharacterEncodingFilter characterEncodingFilter = new
    // OrderedCharacterEncodingFilter();
    // characterEncodingFilter.setEncoding(Charsets.UTF_8.name());
    // characterEncodingFilter.setForceEncoding(true);
    // return characterEncodingFilter;
    // }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {

        return new StringHttpMessageConverter(Charsets.UTF_8);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {

        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        // 为mapper注册一个带有SerializerModifier的Factory，此modifier主要做的事情为：当序列化类型为array，list、set时，当值为空时，序列化成[]
        mapper.getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier());
        // 注册double的转换
        SimpleModule module = new SimpleModule();
        module.addSerializer(Double.class, new DoubleSerializer());
        mapper.registerModule(module);
        converter.setSupportedMediaTypes(ImmutableList.of(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
        return converter;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();
        String maxFileSize = appConfig.getProperty("multipart.max-file-size", "10MB");
        String maxRequestSize = appConfig.getProperty("multipart.max-file-size", "10MB");
        LOGGER.info("multipart.max-file-size: {}, multipart.max-file-size: {}", maxFileSize, maxRequestSize);

        factory.setMaxFileSize(maxFileSize);
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new ErrorPagesCustomizer();
    }

    private static class ErrorPagesCustomizer implements EmbeddedServletContainerCustomizer {

        @Override
        public void customize(ConfigurableEmbeddedServletContainer factory) {

            factory.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
            factory.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/401"));
            factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
        }
    }

    @Bean
    public HandlerExceptionResolver exceptionResolver() {

        return new CustomSimpleMappingExceptionResolver();
    }

    private static class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

        @Override
        protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                Object handler, Exception ex) {

            if (Tools.isAjax(request)) {
                try {
                    PrintWriter writer = response.getWriter();
                    writer.write(ex.getMessage());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            return super.doResolveException(request, response, handler, ex);
        }
    }

    class DoubleSerializer extends JsonSerializer<Double> {

        DecimalFormat df = new DecimalFormat("0.00");

        @Override
        public void serialize(Double value, JsonGenerator jgen, SerializerProvider serializers)
                throws IOException, JsonProcessingException {

            jgen.writeNumber(df.format(value));
        }

    }
}
