package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
//@RequestMapping(value = "/pedidos/{codigoPedido}")
@RequestMapping(path="/v1/pedidos/{codigoPedido}")//, produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    @Autowired
    private FluxoPedidoService fluxoPedido;

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @Override
	@PutMapping("/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
    	fluxoPedido.confirmar(codigoPedido);
    	
    	return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @Override
	@PutMapping("/confirmacao/sendSMS")
    public ResponseEntity<Void> confirmarSMS(@PathVariable String codigoPedido) {
    	fluxoPedido.confirmarSMS(codigoPedido);
    	return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @Override
	@PutMapping("/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
    	fluxoPedido.cancelar(codigoPedido);
    	return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @Override
	@PutMapping("/entrega")
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
    	fluxoPedido.entregar(codigoPedido);
    	return ResponseEntity.noContent().build();
    }


}