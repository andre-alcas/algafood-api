package com.algaworks.algafood.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

	final static String className = " foto(s) do(s) produto(s) do(s) restaurante(s) ";
	final static String classSuportNameRestaurante = " restaurante ";
	final static String classSuportNameProduto = " produto ";
	
	@ApiOperation("Atualiza uma"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=200,message=className+"atualizada"),
		@ApiResponse(code=404,message=classSuportNameRestaurante+"ou"+classSuportNameProduto+"não encontrado(s)",response = Problem.class),
	})
	FotoProdutoModel atualizarFoto(@ApiParam(value="ID de um"+classSuportNameRestaurante, example="1", required = true) Long restauranteId, 
			@ApiParam(value="ID de um"+classSuportNameProduto, example="1", required = true) Long produtoId,
			@ApiParam(name="corpo",value="Representação de uma nova"+className,required=true) FotoProdutoInput fotoProdutoInput,
			@ApiParam(example = "Arquivo da foto do produto(máximo 5000KB, apenas JPG e PNG)",required = true) MultipartFile arquivo) throws IOException;
	
	
	@ApiOperation(value="Busca uma"+className+"por ID", produces="application/json, image/jpeg, image/png")
	@ApiResponses({
		@ApiResponse(code=400,message="ID do"+classSuportNameRestaurante+"ou"+classSuportNameProduto+"inválido",response = Problem.class),
		@ApiResponse(code=404,message=className+"não encontrado(s)",response = Problem.class),
	})
	 FotoProdutoModel buscar(@ApiParam(value="ID de um"+classSuportNameRestaurante, example="1", required = true) Long restauranteId, 
				@ApiParam(value="ID de um"+classSuportNameProduto, example="1", required = true) Long produtoId );
	
	@ApiOperation(value="Busca uma"+className+"por ID", hidden = true)//oculta da documentação e mostra os detalhes só de buscar já que é a mesma operação só que com header
	ResponseEntity<?> servir( Long restauranteId,
			Long produtoId, 
			String acceptHeader) throws HttpMediaTypeNotAcceptableException;
	
	@ApiOperation("Exclui um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"excluída"),
		@ApiResponse(code=400,message=classSuportNameRestaurante+"ou"+classSuportNameProduto+"não encontrado",response = Problem.class),
		@ApiResponse(code=404,message=className+"não encontrada"),
	})
	void deletar(@ApiParam(value="ID de um"+classSuportNameRestaurante, example="1", required = true) Long restauranteId, 
			@ApiParam(value="ID de um"+classSuportNameProduto, example="1", required = true) Long produtoId );
		
}