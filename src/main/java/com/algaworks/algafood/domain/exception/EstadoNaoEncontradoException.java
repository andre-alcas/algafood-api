package com.algaworks.algafood.domain.exception;

//Response já vem pela herança
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	public EstadoNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de Estado com código %d", estadoId));
	}
}
