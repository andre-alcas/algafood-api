package com.algaworks.algafood.domain.exception;

//Response já vem pela herança
public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public FotoProdutoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe foto para o produto de código %d e para o restaurante de código %d", 
                produtoId, restauranteId));
    }
}
