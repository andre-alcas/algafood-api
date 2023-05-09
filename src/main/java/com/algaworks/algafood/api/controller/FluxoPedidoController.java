package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void confirmar(@PathVariable String codigoPedido) {
    	fluxoPedido.confirmar(codigoPedido);
    }

    @Override
	@PutMapping("/confirmacao/sendSMS")
    public void confirmarSMS(@PathVariable String codigoPedido) {
    	fluxoPedido.confirmarSMS(codigoPedido);
    }

    @Override
	@PutMapping("/cancelamento")
    public void cancelar(@PathVariable String codigoPedido) {
    	fluxoPedido.cancelar(codigoPedido);
    }

    @Override
	@PutMapping("/entrega")
    public void entregar(@PathVariable String codigoPedido) {
    	fluxoPedido.entregar(codigoPedido);
    }


}