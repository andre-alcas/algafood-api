package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

	final static String className = " produto(s) do restaurante(s) ";
	final static String classSuportName = " restaurante ";

	@ApiOperation(value="Lista os"+className)
	List<ProdutoModel> listar( @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem",
            example = "false", defaultValue = "false")
			boolean incluirInativos);

	@ApiOperation("Busca um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=400,message="ID do"+classSuportName+"ou"+className+"inválido",response = Problem.class),
		@ApiResponse(code=404,message=classSuportName+"ou"+className+"não encontrado",response = Problem.class),
	})
	ProdutoModel buscar(@ApiParam(value="ID de um"+classSuportName, example="1", required = true) Long restauranteId, @ApiParam(value="ID de um"+className, example="1", required = true) Long produtoId );

	@ApiOperation("Adiciona um"+className)
	@ApiResponses({
		@ApiResponse(code=200,message=className+"adiconado"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	ProdutoModel adicionar(@ApiParam(value="ID de um"+classSuportName, example="1", required = true) Long restauranteId,
			@ApiParam(name="corpo",value="Representação de um novo"+className,required=true) ProdutoInput produtoInput);

	@ApiOperation("Atualiza um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=200,message=className+"atualizado"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	ProdutoModel atualizar(@ApiParam(value="ID de um"+classSuportName, example="1", required = true) Long restauranteId,
			@ApiParam(value="ID de um"+className, example="1") Long produtoId,
			@ApiParam(name="corpo",value="Representação de um novo"+className,required=true) ProdutoInput produtoInput) ;

}