package com.algaworks.algafood.domain.exception;

//Response já vem pela herança
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem); 
	}
	public CozinhaNaoEncontradaException(Long cidadeId) {
		this(String.format("Não existe um cadastro de Estado com código %d", cidadeId));
	}
}
