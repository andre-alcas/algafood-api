package com.algaworks.algafood.domain.exception;

//Response já vem pela herança
public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem); 
	}
	public PedidoNaoEncontradoException(Long pedidoId) {
		this(String.format("Não existe um cadastro de Pedido com código %d", pedidoId));
	}
}
