package br.com.locadoracarros.carrental.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private Predicate<RequestHandler> apis() {
		return (Predicate<RequestHandler>) RequestHandlerSelectors.basePackage("br.com.locadoracarros.carrental");
	}

	private ApiInfo apiInfo(){

		ApiInfo apiInfo = new ApiInfoBuilder()
				.title ("API para locadora de carros")
				.description ("Essa é a API para a locação de carros")
				.license ("Apache License Version 2.0")
				.licenseUrl ("https://www.apache.org/licenses/LICENSE-2.0")
				.termsOfServiceUrl("/service.html")
				.version("1.0.0")
				.contact(new Contact("Bernardo Matuchewski","https://github.com/BerPGR", "bernardopgr01@gmail.com"))
				.build();

		return apiInfo;
	}

}
