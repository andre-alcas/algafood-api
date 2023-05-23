package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	final static String className = " forma(s) de pagamento(s) do restaurante(s) ";
	final static String classSuportName = " restaurante ";

	@ApiOperation(value="Lista as"+className)
	CollectionModel<FormaPagamentoModel> listar( @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId) ;


	@ApiOperation("Dessassocia uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"dessassociado com sucesso"),
		@ApiResponse(code=404,message=classSuportName+"ou"+className+"não encontrado",response = Problem.class),
	})
	public ResponseEntity<Void> desassociar(@ApiParam(value="ID de um"+classSuportName, example="1") Long restauranteId, @ApiParam(value="ID de uma"+className, example="1") Long formaPagamentoId );


	@ApiOperation("Associa uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"associado com sucesso"),
		@ApiResponse(code=404,message=classSuportName+"ou"+className+"não encontrado",response = Problem.class),
	})
	public ResponseEntity<Void> associar(@ApiParam(value="ID de um"+classSuportName, example="1") Long restauranteId, @ApiParam(value="ID de uma"+className, example="1") Long formaPagamentoId );


}