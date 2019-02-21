package com.ng.crud.cfg;

import javax.ws.rs.ext.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Provider
@EnableSwagger2
@SwaggerDefinition(
        info = @Info(
                description = "Gets the Events",
                version = "V12.0.14",
                title = "The Events API",
                termsOfService = "http://me.io/terms.html",
                contact = @Contact(
                   name = "me", 
                   email = "me@me.io", 
                   url = "http://me.io"
                ),
                license = @License(
                   name = "Apache 2.0", 
                   url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json"},
        produces = {"application/json"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        tags = {
                @Tag(name = "Events", description = "Events Tag")
        }, 
        externalDocs = @ExternalDocs(value = "Me", url = "http://me.io/me.html")
)

public class SwaggerConfig
{
	@Bean
    public Docket api()
	{
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(this.apiInfo());
        /*  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();*/
    }
	private ApiInfo apiInfo()
	{
		return new ApiInfoBuilder().title("boot2Crud API")
				.description("boot2Crud REST API")
	            .version("1.0.17")
	            .license("Apache License Version 2.0")
				.build();
	}
}
