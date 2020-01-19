package dtu.ws18;
import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import springfox.documentation.builders.PathSelectors;
        import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
        import springfox.documentation.spring.web.plugins.Docket;
        import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("dtu.ws18.restcontrollers"))
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInformation()).useDefaultResponseMessages(false);
    }
    private ApiInfo getApiInformation(){
        return new ApiInfo("DTUPay REST API",
                "This is a DTUPay API created using Spring Boot",
                "1.0",
                "API Terms of Service URL",
                new Contact("Emil Vinkel", "http://fastmoney-18.compute.dtu.dk:8282/", "s175107@student.dtu.dk"),
                "API License",
                "API License URL",
                Collections.emptyList()
        );
    }
}
