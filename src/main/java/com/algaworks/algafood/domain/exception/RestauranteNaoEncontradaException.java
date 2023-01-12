package com.algaworks.algafood.domain.exception;

//Response já vem pela herança
public class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncontradaException(String mensagem) {
		super(mensagem); 
	}
	public RestauranteNaoEncontradaException(Long cidadeId) {
		this(String.format("Não existe um cadastro de Estado com código %d", cidadeId));
	}
}
