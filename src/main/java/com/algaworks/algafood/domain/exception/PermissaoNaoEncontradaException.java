package com.algaworks.algafood.domain.exception;

//Response já vem pela herança
public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	public PermissaoNaoEncontradaException(Long grupoId) {
		this(String.format("Não existe um cadastro de Permissão com código %d", grupoId));
	}
}
