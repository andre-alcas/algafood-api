package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

final static String className = " estado ";
	
	@ApiOperation(value="Lista os"+className+"s")
	List<EstadoModel> listar();

	@ApiOperation("Busca um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=400,message="ID do"+className+"inválido",response = Problem.class),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	EstadoModel buscar(@ApiParam(value="ID de um"+className, example="1",required=true) Long estadoId);

	@ApiOperation("Adiciona um novo" + className)
	@ApiResponses({
		@ApiResponse(code=201,message=className+"cadastrado"),
	})
	EstadoModel adicionar(@ApiParam(name="corpo",value="Representação de um novo"+className,required=true) EstadoInput estadoInput);

	@ApiOperation("Atualiza um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=200,message=className+"atualizado"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	EstadoModel atualizar(
			@ApiParam(value="ID de um"+className, example="1")
			Long estadoId, 
			@ApiParam(name="corpo",value="Representação de um novo"+className,required=true)
			EstadoInput estadoInput);


	@ApiOperation("Exclui um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"excluído"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	void remover(@ApiParam(value="ID de um"+className, example="1",required=true) Long estadoId);
}