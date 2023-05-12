package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	final static String className = " restaurante ";

	@ApiOperation(value = "Lista os" + className + "s", response = RestauranteBasicoModelOpenApi.class) // para quando
																										// usar JsonView
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome", name = "projecao", paramType = "query", type = "string") })
	//@JsonView(RestauranteView.Resumo.class)
	CollectionModel<RestauranteBasicoModel> listar();

	@ApiOperation(value = "Lista"+className+"s", hidden = true)
	CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();
	//List<RestauranteModel> listarApenasNomes();

	@ApiOperation(value = "Busca um" + className + "por ID", response = RestauranteBasicoModelOpenApi.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "ID do" + className + "inválido", response = Problem.class),
			@ApiResponse(code = 404, message = className + "não encontrado", response = Problem.class), })
	RestauranteModel buscar(
			@ApiParam(value = "ID de um" + className, example = "1", required = true) Long restauranteId);

	@ApiOperation(value = "Adiciona um novo" + className, response = RestauranteBasicoModelOpenApi.class)
	@ApiResponses({ @ApiResponse(code = 201, message = className + "cadastrado"), })
	RestauranteModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo"
			+ className, required = true) RestauranteInput restauranteInput);

	@ApiOperation(value = "Atualiza um" + className + "por ID", response = RestauranteBasicoModelOpenApi.class)
	@ApiResponses({ @ApiResponse(code = 200, message = className + "atualizado"),
			@ApiResponse(code = 404, message = className + "não encontrado", response = Problem.class), })
	RestauranteModel atualizar(@ApiParam(value = "ID de um" + className, example = "1") Long restauranteId,
			@ApiParam(name = "corpo", value = "Representação de um novo"
					+ className, required = true) RestauranteInput restauranteInput);

	@ApiOperation("Inativa um ou mais" + className + "s")
	@ApiResponses({ @ApiResponse(code = 204, message = className + "inativado(s) com sucesso"),
			@ApiResponse(code = 404, message = className + "não encontrado(s)", response = Problem.class), })
	void inativarMuitos(@ApiParam(value = "ID de um ou mais" + className
			+ "s", example = "[1,2,3]", required = true) List<Long> restauranteIds);

	@ApiOperation("Ativa um ou mais" + className + "s")
	@ApiResponses({ @ApiResponse(code = 204, message = className + "ativado(s) com sucesso"),
			@ApiResponse(code = 404, message = className + "não encontrado(s)", response = Problem.class), })
	void ativarMuitos(@ApiParam(value = "ID de um ou mais" + className
			+ "s", example = "[1,2,3]", required = true) List<Long> restauranteIds);

	@ApiOperation("Ativa um" + className + "por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = className + "ativado com sucesso"),
			@ApiResponse(code = 404, message = className + "não encontrado", response = Problem.class), })
	ResponseEntity<Void> ativar(@ApiParam(value = "ID de um" + className, example = "1") Long restauranteId);

	@ApiOperation("Inativa um" + className + "por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = className + "inativado com sucesso"),
			@ApiResponse(code = 404, message = className + "não encontrado", response = Problem.class), })
	ResponseEntity<Void> inativar(@ApiParam(value = "ID de um" + className, example = "1") Long restauranteId);

	@ApiOperation("Abre um" + className + "por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = className + "aberto com sucesso"),
			@ApiResponse(code = 404, message = className + "não encontrado", response = Problem.class), })
	ResponseEntity<Void> abertura(@ApiParam(value = "ID de um" + className, example = "1") Long restauranteId);

	@ApiOperation("Fecha um" + className + "por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = className + "fechado com sucesso"),
			@ApiResponse(code = 404, message = className + "não encontrado", response = Problem.class), })
	ResponseEntity<Void> fechamento(@ApiParam(value = "ID de um" + className, example = "1") Long restauranteId);

}