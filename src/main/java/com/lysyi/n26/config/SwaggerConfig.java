package com.lysyi.n26.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lysyi.n26.domain.Transaction;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	private Contact contact() {
		return new Contact("Oleksandr Lysyi", 
				 "https://www.linkedin.com/in/oleksandr-lysyi/",
				 "armiax@gmail.com");
	}
	
	@Value("${spring.application.name}")
    private String applicationName;
	@Value("${spring.application.version}")
    private String applicationVersion;
	
	@Bean
	public Docket newsApi() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.lysyi"))
				.build();
	}
	
	private ApiInfo apiInfo() {
		
		return new ApiInfoBuilder()
				.title(this.applicationName)
				.description(this.applicationName)
				.contact(contact())
				.version(applicationVersion)
				.build();
	}
	


}