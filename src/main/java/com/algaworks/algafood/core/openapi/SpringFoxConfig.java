package com.algaworks.algafood.core.openapi;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.GruposModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PermissoesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.ProdutosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.UsuariosModelOpenApi;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.openapi.model.CidadesModelV2OpenApi;
import com.algaworks.algafood.api.v2.openapi.model.CozinhasModelV2OpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer  {

	@Bean
	public Docket apiDocketV1() {
		TypeResolver typeResolver = new TypeResolver();
		   return new Docket(DocumentationType.OAS_30)
				    .groupName("V1")
			        .select()
			        .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))//busca só os controladores dentro desse pacote
			        .paths(PathSelectors.ant("/v1/**"))
			        //.paths(PathSelectors.any())
//			          .paths(PathSelectors.ant("/restaurantes/*"))
			        .build()
			        .useDefaultResponseMessages(false)
			        .globalResponses(HttpMethod.GET, globalGetResponseMessages())
			        .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
			        .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
			        .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
//			        .globalRequestParameters(Collections.singletonList(
//			                new RequestParameterBuilder()
//			                        .name("campos")
//			                        .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//			                        .in(ParameterType.QUERY)
//			                        .required(true)
//			                        .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//			                        .build())
//			        )
			        .additionalModels(typeResolver.resolve(Problem.class))
			        .ignoredParameterTypes(ServletWebRequest.class,
			        		URL.class,
			        		URI.class,
			        		URLStreamHandler.class,
			        		Resource.class,
			        		File.class,
			        		InputStream.class)//para ignorar ServletWebRequest dos buscar por Id(exemplo: formaDePagamentoController)
			        .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
			        .directModelSubstitute(Links.class,LinksModelOpenApi.class)
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        		typeResolver.resolve(PagedModel.class,CozinhaModel.class),
			        		CozinhasModelOpenApi.class
			        		))//substituindo Page de CozinhaModel para CozinhasModelOpenApi //Page<CozinhaModel> -> CozinhasModelOpenApi
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        		typeResolver.resolve(PagedModel.class,PedidoResumoModel.class),
			        		PedidosResumoModelOpenApi.class
			        		))
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        		typeResolver.resolve(CollectionModel.class,CidadeModel.class),
			        		CidadesModelOpenApi.class
			        		))
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        		typeResolver.resolve(CollectionModel.class,EstadoModel.class),
			        		EstadosModelOpenApi.class
			        		))
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        	    typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
			        	    FormasPagamentoModelOpenApi.class))
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        	    typeResolver.resolve(CollectionModel.class, GrupoModel.class),
			        	    GruposModelOpenApi.class))
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        	        typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
			        	        PermissoesModelOpenApi.class))
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        		    typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
			        		    ProdutosModelOpenApi.class))
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        	    typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
			        	    RestaurantesBasicoModelOpenApi.class))
			        .alternateTypeRules(AlternateTypeRules.newRule(
			        	        typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
			        	        UsuariosModelOpenApi.class))
			        .apiInfo(apiInfoV1())
			        .tags(
			        		new Tag("Cidades", "Gerencia as cidades"),
			                new Tag("Grupos", "Gerencia os grupos de usuários"),
			                new Tag("Cozinhas", "Gerencia as cozinhas"),
			                new Tag("Formas de Pagamento", "Gerencia as formas de pagamentos"),
			                new Tag("Pedidos", "Gerencia os pedidos"),
			                new Tag("Restaurantes", "Gerencia os restaurantes"),
			                new Tag("Estados", "Gerencia os estados"),
			                new Tag("Produtos", "Gerencia os produtos de um restaurante"),
			                new Tag("Usuários", "Gerencia os usuários"),
			                new Tag("Estatísticas", "Estatísticas da AlgaFoodAPI"),
			                new Tag("Permissões", "Gerencia as permissões"));
				//.apis(RequestHandlerSelectors.any())//busca qualquer controlador (inclusive do spring boot)
	}
	
	@Bean
	public Docket apiDocketV2() {
		var typeResolver = new TypeResolver();

		return new Docket(DocumentationType.OAS_30)
				.groupName("V2")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
				.paths(PathSelectors.ant("/v2/**"))
				.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class,
						URL.class, URI.class, URLStreamHandler.class, Resource.class,
						File.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(PagedModel.class, CozinhaModelV2.class),
					    CozinhasModelV2OpenApi.class))

					.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, CidadeModelV2.class),
					        CidadesModelV2OpenApi.class))

					.apiInfo(apiInfoV2())
					        
					.tags(new Tag("Cidades", "Gerencia as cidades"),
					        new Tag("Cozinhas", "Gerencia as cozinhas"));
	}

	private List<Response> globalGetResponseMessages() {
		  return Arrays.asList(
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          .description("Erro interno do Servidor")
		          .representation( MediaType.APPLICATION_JSON )
		          .apply(getProblemaModelReference())
		          .build(),
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		          .description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          .build()
		  );
		}
	private List<Response> globalPostPutResponseMessages() {
		  return Arrays.asList(
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          .description("Erro interno do Servidor")
		          .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
		          .build(),
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		          .description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          .build(),
		          new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		          .description("Requisição inválida (erro do cliente)")
		          .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
		          .build(),
		          new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
		          .description("Tipo de mídia não suportada")
		          .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
		          .build()
		  );
		}
	private List<Response> globalDeleteResponseMessages() {
		  return Arrays.asList(
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          .description("Erro interno do Servidor")
		          .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
		          .build(),
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		          .description("Requisição inválida (erro do cliente)")
		          .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
		          .build()
		  );
		}

	@Bean  //Resolver offsetdatetime Exemplo na documentacao
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}

	private Consumer<RepresentationBuilder> getProblemaModelReference() {
	    return r -> r.model(m -> m.name("Problema")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("Problema").namespace("com.algaworks.algafood.api.exceptionhandler")))));
	}
	
	public ApiInfo apiInfoV1() {
		return new ApiInfo("AlgaFood API V1", "API aberta para cliente e restaurantes.<br>"
				+ "<strong>Essa versão da API está depreciada e deixará de existir a partir de 01/01/2030.Use a versão mais atual da API.",
				"1", 
				"AlgaWorks", 
				"hhtps://www.andrecarvalho.com.br", 
				"contato@andrecarvalho.com.br",
				"1" );
	}

//	public ApiInfo apiInfoV1() {
//		return new ApiInfo("AlgaFood API V1", "API aberta para cliente e restaurantes", "1", "AlgaWorks", "hhtps://www.andrecarvalho.com.br", "contato@andrecarvalho.com.br","1" );
////				.title("AlgaFood API")
////				.description("API aberta para cliente e restaurantes")
////				.version("1")
////				.contact(new Contact("AlgaWorks","hhtps://www.andrecarvalho.com.br","contato@andrecarvalho.com.br"))
////				.build();
//	}
	public ApiInfo apiInfoV2() {
		return new ApiInfo("AlgaFood API V2", "API aberta para cliente e restaurantes", "2", "AlgaWorks", "hhtps://www.andrecarvalho.com.br", "contato@andrecarvalho.com.br","1" );
	}

}