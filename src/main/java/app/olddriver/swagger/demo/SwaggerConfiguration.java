package app.olddriver.swagger.demo;


import com.fasterxml.classmate.TypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.lang.reflect.WildcardType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * swagger配置
 *
 * @author zhanghuan372
 */
@Slf4j
@Configuration
@EnableSwagger2
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
public class SwaggerConfiguration {

    @Resource
    private TypeResolver typeResolver;


    private final List<ResponseMessage> globalResponses = Arrays.asList(
            new ResponseMessageBuilder()
                    .code(200)
                    .message("OK")
                    .build(),
            new ResponseMessageBuilder()
                    .code(200)
                    .message("Bad Request")
                    .build(),
            new ResponseMessageBuilder()
                    .code(200)
                    .message("Internal Error")
                    .build());


    @Bean
    public Docket productApi() {
        final ApiInfo apiInfo = new ApiInfo("Api Documentation", "DEMO项目", "0.0.1-SNAPSHOT",
                null,
                new Contact("tobecodingmaster", "https://www.olddriver.app/", "tobecodingmaster@gmail.com"),
                "Copyright © tobecodingmaster All Rights Reserved",
                "https://www.olddriver.app/",
                new ArrayList<>());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/user/**"))
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                //.genericModelSubstitutes(Response.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalResponses)
                .globalResponseMessage(RequestMethod.POST, globalResponses)
                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
                .globalResponseMessage(RequestMethod.PATCH, globalResponses)
                .enableUrlTemplating(true)
                .apiInfo(apiInfo);
    }


    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

}