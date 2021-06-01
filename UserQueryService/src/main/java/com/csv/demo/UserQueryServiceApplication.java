package com.csv.demo;

import java.util.Collections;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
@ComponentScan("com")
@EnableWebMvc
public class UserQueryServiceApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(UserQueryServiceApplication.class, args);
	}

	@Autowired
	ServletContext context; 

	@Bean
	public Docket swaggerConfiguration() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2).pathProvider(new RelativePathProvider(context) {
			@Override
			public String getApplicationBasePath() {
				return "/UserSearch";
			}}).select()
				.apis(RequestHandlerSelectors.basePackage("com"))
				.paths(PathSelectors.any()) 
				.build().apiInfo(apiDetails());
		return docket;
	}


	private ApiInfo apiDetails() {
		return new ApiInfo("User Search",
				"User Search API" ,
				"V1",
				"https://www.economical.com/en/terms-of-use",
				null,
				"EconomicalInsurance",
				"/UserSearch",
				Collections.emptyList());
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		// Simple strategy: only path extension is taken into account
		configurer.favorPathExtension(true).
		ignoreAcceptHeader(true).
		useJaf(false).
		defaultContentType(MediaType.APPLICATION_JSON).
		mediaType("html", MediaType.TEXT_HTML).
		mediaType("text", MediaType.TEXT_PLAIN).
		mediaType("xml", MediaType.APPLICATION_XML).
		mediaType("json", MediaType.APPLICATION_JSON);
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
