package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um cadastro de restaurante com código %d";
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
//		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
//				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId)));

		restaurante.setCozinha(cozinha);
	
		return restauranteRepository.save(restaurante); 
	}
	
//	public void excluir(Long restauranteId) {
//		try {
//			restauranteRepository.deleteById(restauranteId);
//		}catch (EmptyResultDataAccessException e) {
//			throw new EntidadeNaoEncontradaException(
//					String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId));
//		}catch (DataIntegrityViolationException e) {
//			throw new EntidadeEmUsoException(
//					String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
//		}
//	}
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(()-> new RestauranteNaoEncontradoException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
	}
}
