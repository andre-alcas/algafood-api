package com.algaworks.algafood.domain.exception;

//Response já vem pela herança
public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

//	public PedidoNaoEncontradoException(String mensagem) {
//		super(mensagem);
//	}
	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("Não existe um cadastro de Pedido com código %d", codigoPedido));
	}
}
