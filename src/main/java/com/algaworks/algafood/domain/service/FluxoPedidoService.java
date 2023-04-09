package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;
import com.algaworks.algafood.domain.service.EnvioSmsService.SMS;
import com.algaworks.algafood.infrastructure.repository.service.email.FakeEnvioEmailService;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;
    
    @Autowired
    private EnvioEmailService envioEmailService;
    
    @Autowired
    private EnvioSmsService envioSmsService;
      
    @Transactional
    public void confirmar(String codigoPedido) {
       Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
       pedido.confirmar();
       
       var mensagem = Mensagem.builder()
    		   .assunto(pedido.getRestaurante().getNome()+ "- Pedido confirmado")
    		   .corpo("pedido-confirmado.html")
    		   .variavel("pedido", pedido)
    		   .destinatario(pedido.getCliente().getEmail())
    		   .build();
     
       envioEmailService.enviar(mensagem);
    }
    
    @Transactional
    public void confirmarSMS(String codigoPedido) {
       Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
       pedido.confirmar();
       
       //inserir to e from
       var mensagem = SMS.builder()
    		   .to("")
    		   .from("")
    		   .corpo("Hello from Twilio Alcas - teste enviado com sucesso")
    		   .build();
       
       envioSmsService.enviar(mensagem);
       
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