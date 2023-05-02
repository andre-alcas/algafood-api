package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.annotations.Api;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig implements WebMvcConfigurer  {

	@Bean
	public Docket apiDocket() {
		   return new Docket(DocumentationType.OAS_30)
			        .select()
			        .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))//busca só os controladores dentro desse pacote
			        .paths(PathSelectors.any())
//			          .paths(PathSelectors.ant("/restaurantes/*"))
			        .build()
			        .apiInfo(apiInfo())
			        .tags(new Tag("Cidades", "Gerencia as Cidades"));
				//.apis(RequestHandlerSelectors.any())//busca qualquer controlador (inclusive do spring boot)
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfo("AlgaFood API", "API aberta para cliente e restaurantes", "1", "AlgaWorks", "hhtps://www.andrecarvalho.com.br", "contato@andrecarvalho.com.br","1" );
//				.title("AlgaFood API")
//				.description("API aberta para cliente e restaurantes")
//				.version("1")
//				.contact(new Contact("AlgaWorks","hhtps://www.andrecarvalho.com.br","contato@andrecarvalho.com.br"))
//				.build();
	}

}