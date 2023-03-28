package com.algaworks.algafood.domain.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {
	
	//private static final String MSG_PRODUTO_EM_USO = "Produto de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(foto.getProduto().getRestaurante().getId(),foto.getProduto().getId());
		
		if(fotoExistente.isPresent()) {
			produtoRepository.delete(fotoExistente.get());
		}
		
		return produtoRepository.save(foto);
	}
	
}
