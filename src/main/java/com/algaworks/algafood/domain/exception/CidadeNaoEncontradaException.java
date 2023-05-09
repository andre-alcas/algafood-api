package com.algaworks.algafood.domain.exception;

//Response já vem pela herança
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format("Não existe um cadastro de Estado com código %d", cidadeId));
	}
}
