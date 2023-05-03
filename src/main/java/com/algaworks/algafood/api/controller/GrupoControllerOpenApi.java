package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	final static String className = " grupo ";
	
	@ApiOperation(value="Lista os"+className+"s")
	List<GrupoModel> listar();

	@ApiOperation("Busca um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=400,message="ID do"+className+"inválido",response = Problem.class),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	GrupoModel buscar(@ApiParam(value="ID de um"+className, example="1") Long grupoId);

	@ApiOperation("Adiciona um novo" + className)
	@ApiResponses({
		@ApiResponse(code=201,message=className+"cadastrado"),
	})
	GrupoModel adicionar(@ApiParam(name="corpo",value="Representação de um novo"+className) GrupoInput grupoInput);

	@ApiOperation("Atualiza um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=200,message=className+"atualizado"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	GrupoModel atualizar(
			@ApiParam(value="ID de um"+className, example="1")
			Long grupoId, 
			@ApiParam(name="corpo",value="Representação de um novo"+className)
			GrupoInput grupoInput);


	@ApiOperation("Exclui um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"excluído"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	void remover(@ApiParam(value="ID de um"+className, example="1") Long grupoId);

}