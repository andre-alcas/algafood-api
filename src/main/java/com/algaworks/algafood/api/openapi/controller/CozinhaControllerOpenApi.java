package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	final static String className = " cozinha ";

	@ApiOperation(value="Lista as"+className+"s com paginação")
	PagedModel<CozinhaModel> listar(Pageable pageable);

	@ApiOperation("Busca uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=400,message="ID da"+className+"inválida",response = Problem.class),
		@ApiResponse(code=404,message=className+"não encontrada",response = Problem.class),
	})
	CozinhaModel buscar(@ApiParam(value="ID de uma"+className, example="1",required=true) Long cozinhaId);

	@ApiOperation("Adiciona uma nova" + className)
	@ApiResponses({
		@ApiResponse(code=201,message=className+"cadastrada"),
	})
	CozinhaModel adicionar(@ApiParam(name="corpo",value="Representação de uma nova"+className,required=true) CozinhaInput cozinhaInput);

	@ApiOperation("Atualiza uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=200,message=className+"atualizada"),
		@ApiResponse(code=404,message=className+"não encontrada",response = Problem.class),
	})
	CozinhaModel atualizar(
			@ApiParam(value="ID de uma"+className, example="1")
			Long cozinhaId,
			@ApiParam(name="corpo",value="Representação de uma nova"+className,required=true)
			CozinhaInput cozinhaInput);


	@ApiOperation("Exclui uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"excluída"),
		@ApiResponse(code=404,message=className+"não encontrada",response = Problem.class),
	})
	void remover(@ApiParam(value="ID de uma"+className, example="1",required=true) Long cozinhaId);

}