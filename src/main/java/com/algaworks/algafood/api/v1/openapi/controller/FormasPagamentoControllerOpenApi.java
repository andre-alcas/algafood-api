package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de Pagamento")
public interface FormasPagamentoControllerOpenApi {

	final static String className = " forma de pagamento ";

	@ApiOperation(value="Lista as"+className+"s")//, response =  FormasPagamentoModelOpenApi.class) nao funciona
	@ApiResponses({
			@ApiResponse( response = FormasPagamentoModelOpenApi.class, code = 200, message = "OK")
			})
	//ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);
	ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

	@ApiOperation("Busca uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=400,message="ID da"+className+"inválida",response = Problem.class),
		@ApiResponse(code=404,message=className+"não encontrada",response = Problem.class),
	})
	ResponseEntity<FormaPagamentoModel> buscar(@ApiParam(value="ID de uma"+className, example="1",required=true) Long formaPagamentoId, ServletWebRequest request);

	@ApiOperation("Adiciona uma nova" + className)
	@ApiResponses({
		@ApiResponse(code=201,message=className+"cadastrada"),
	})
	FormaPagamentoModel adicionar(@ApiParam(name="corpo",value="Representação de uma nova"+className,required=true) FormaPagamentoInput formaPagamentoInput);

	@ApiOperation("Atualiza uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=200,message=className+"atualizada"),
		@ApiResponse(code=404,message=className+"não encontrada",response = Problem.class),
	})
	FormaPagamentoModel atualizar(
			@ApiParam(value="ID de uma"+className, example="1")
			Long formaPagamentoId,
			@ApiParam(name="corpo",value="Representação de uma nova"+className,required=true)
			FormaPagamentoInput formaPagamentoInput);


	@ApiOperation("Exclui uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"excluída"),
		@ApiResponse(code=404,message=className+"não encontrada",response = Problem.class),
	})
	void remover(@ApiParam(value="ID de uma"+className, example="1",required=true) Long formaPagamentoId);

	@ApiOperation("Busca data e hora da última "+className+"cadastrada - Utilidade")
	ResponseEntity<?> buscarUltimo();
}