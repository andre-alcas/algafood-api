package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(value = "/pedidos/{codigoPedido}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    @Autowired
    private FluxoPedidoService fluxoPedido;

    @Override
	@PutMapping("/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
    	fluxoPedido.confirmar(codigoPedido);
    	
    	return ResponseEntity.noContent().build();
    }

    @Override
	@PutMapping("/confirmacao/sendSMS")
    public ResponseEntity<Void> confirmarSMS(@PathVariable String codigoPedido) {
    	fluxoPedido.confirmarSMS(codigoPedido);
    	return ResponseEntity.noContent().build();
    }

    @Override
	@PutMapping("/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
    	fluxoPedido.cancelar(codigoPedido);
    	return ResponseEntity.noContent().build();
    }

    @Override
	@PutMapping("/entrega")
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
    	fluxoPedido.entregar(codigoPedido);
    	return ResponseEntity.noContent().build();
    }


}