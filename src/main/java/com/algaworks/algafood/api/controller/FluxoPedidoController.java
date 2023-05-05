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
    
    @PutMapping("/confirmacao")
    public void confirmar(@PathVariable String codigoPedido) {
    	fluxoPedido.confirmar(codigoPedido);
    }
    
    @PutMapping("/confirmacao/sendSMS")
    public void confirmarSMS(@PathVariable String codigoPedido) {
    	fluxoPedido.confirmarSMS(codigoPedido);
    }
    
    @PutMapping("/cancelamento")
    public void cancelar(@PathVariable String codigoPedido) {
    	fluxoPedido.cancelar(codigoPedido);
    }
    
    @PutMapping("/entrega")
    public void entregar(@PathVariable String codigoPedido) {
    	fluxoPedido.entregar(codigoPedido);
    }
    
   
}  