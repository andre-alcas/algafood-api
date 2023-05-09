package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EnvioSmsService.SMS;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

//    @Autowired
//    private EnvioEmailService envioEmailService;

    @Autowired
    private EnvioSmsService envioSmsService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigoPedido) {
       Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
       pedido.confirmar();//emite evento aqui
       pedidoRepository.save(pedido);//para disparar evento precisa chamar o save do repository (caracteristica spring data)
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
       pedido.cancelar();//emite evento aqui
       pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(String codigoPedido) {
       Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
       pedido.entregar();
    }

}