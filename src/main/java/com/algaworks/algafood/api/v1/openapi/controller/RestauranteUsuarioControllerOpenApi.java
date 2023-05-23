package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {

	final static String className = " responsavel(s) pelo restaurante(s) ";
	final static String classSuportName = " restaurante ";

	@ApiOperation(value="Lista os"+className)
	CollectionModel<UsuarioModel> listar(@ApiParam(value = "ID do"+classSuportName, example = "1", required = true) Long restauranteId) ;


	@ApiOperation("Dessassocia um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"dessassociado com sucesso"),
		@ApiResponse(code=404,message=classSuportName+"ou"+className+"não encontrado",response = Problem.class),
	})
	public ResponseEntity<Void> desassociar(@ApiParam(value="ID de um"+classSuportName, example="1") Long restauranteId, @ApiParam(value="ID de um"+className, example="1") Long usuarioId );


	@ApiOperation("Associa uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"associado com sucesso"),
		@ApiResponse(code=404,message=classSuportName+"ou"+className+"não encontrado",response = Problem.class),
	})
	public ResponseEntity<Void> associar(@ApiParam(value="ID de um"+classSuportName, example="1") Long restauranteId, @ApiParam(value="ID de um"+className, example="1") Long usuarioId );


}