package com.algaworks.algafood.domain.exception;

//Response já vem pela herança
public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem); 
	}
	public GrupoNaoEncontradoException(Long grupoId) {
		this(String.format("Não existe um cadastro de Grupo com código %d", grupoId));
	}
}
