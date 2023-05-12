package com.algaworks.algafood.api.openapi.controller;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	final static String className = " pedido ";

	@ApiOperation("Cancelamento de pedido")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"cancelado"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	ResponseEntity<Void> cancelar(String codigoPedido);

	@ApiOperation("Confirmação de pedido")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"confirmado"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	ResponseEntity<Void> confirmar(String codigoPedido);


	@ApiOperation("Confirmação de pedido via SMS")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"confirmado por SMS"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
    ResponseEntity<Void> confirmarSMS(String codigoPedido);

	@ApiOperation("Registrar entrega de pedido")
	@ApiResponses({
		@ApiResponse(code=204,message=className+"entregue"),
		@ApiResponse(code=404,message=className+"não encontrado",response = Problem.class),
	})
	ResponseEntity<Void> entregar(String codigoPedido);

}