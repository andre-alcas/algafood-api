package com.algaworks.algafood.domain.service;


import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {
	
	//private static final String MSG_PRODUTO_EM_USO = "Produto de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		
		String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(foto.getProduto().getRestaurante().getId(),foto.getProduto().getId());
		
		
		if(fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();//descarrega tudo que tem na fila do JPA antes de continuar
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(dadosArquivo)
				.build();
		
		fotoStorage.substituir(nomeArquivoExistente,novaFoto);
		
		return foto;
	}
	
	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		
		FotoProduto fotoExistente = produtoRepository
				.findFotoById(restauranteId,produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));;
		
		return fotoExistente;
	
	}
	
}
