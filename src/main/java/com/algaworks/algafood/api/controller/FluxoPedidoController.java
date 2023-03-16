package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(value = "/pedidos/{pedidoId}")
public class FluxoPedidoController {
  
    @Autowired
    private FluxoPedidoService fluxoPedido;
    
    @PutMapping("/confirmacao")
    public void confirmar(@PathVariable Long pedidoId) {
    	fluxoPedido.confirmar(pedidoId);
    }
    
    @PutMapping("/cancelamento")
    public void cancelar(@PathVariable Long pedidoId) {
    	fluxoPedido.cancelar(pedidoId);
    }
    
    @PutMapping("/entrega")
    public void entregar(@PathVariable Long pedidoId) {
    	fluxoPedido.entregar(pedidoId);
    }
    
   
}  