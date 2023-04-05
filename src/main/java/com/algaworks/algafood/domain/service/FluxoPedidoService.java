package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;
    
    @Autowired
    private EnvioEmailService envioEmailService;
    
    @Transactional
    public void confirmar(String codigoPedido) {
       Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
       pedido.confirmar();
       
       var mensagem = Mensagem.builder()
    		   .assunto(pedido.getRestaurante().getNome()+ "- Pedido confirmado")
    		   .corpo("O pedido de código <strong>"+pedido.getCodigo()+"</strong> foi confirmado!")
    		   .destinatario(pedido.getCliente().getEmail())
    		   .build();
       
       envioEmailService.enviar(mensagem);
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