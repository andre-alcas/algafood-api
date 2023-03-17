package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;
    
    @Transactional
    public void confirmar(String codigoPedido) {
       Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
       pedido.confirmar();
    }
    
    @Transactional
    public void cancelar(String codigoPedido) {
       Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido); 
       pedido.cancelar();
    }
    
    @Transactional
    public void entregar(String codigoPedido) {
       Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
       pedido.entregar();
    }
            
}