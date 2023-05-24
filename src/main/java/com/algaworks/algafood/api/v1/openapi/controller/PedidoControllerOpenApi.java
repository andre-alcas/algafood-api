package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	final static String className = " pedido ";


	@ApiOperation(value="Pesquisa os"+className+"s")
	 @ApiImplicitParams({
	    	@ApiImplicitParam(value="Nomes das propriedades para filtrar na resposta, separados por vírgula",
	    			name="campos",
	    			paramType = "query",
	    			type = "string"
	    			)
	    })
	PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,Pageable pageable);

	@ApiOperation("Busca um"+className+"por ID")
	@ApiResponses({
		@ApiResponse(code=400,message="ID do"+className+"inválido",response = Problem.class),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	 @ApiImplicitParams({
	    	@ApiImplicitParam(value="Nomes das propriedades para filtrar na resposta, separados por vírgula",
	    			name="campos",
	    			paramType = "query",
	    			type = "string"
	    			)
	    })
	PedidoModel buscar(@ApiParam(value="ID de um"+className, example="1",required=true) String codigoPedido);

	@ApiOperation("Adiciona um novo" + className)
	@ApiResponses({
		@ApiResponse(code=201,message=className+"cadastrado"),
	})
	PedidoModel adicionar(@ApiParam(name="corpo",value="Representação de um novo"+className,required=true) PedidoInput pedidoInput);

}