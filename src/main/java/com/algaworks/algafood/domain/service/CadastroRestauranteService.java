package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um cadastro de restaurante com código %d";
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
//		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
//				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId)));

		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
	
		return restauranteRepository.save(restaurante); 
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		//restauranteAtual.setAtivo(true);
		restauranteAtual.ativar();
		//não precisa fazer o save porque o JPA ainda está gerenciando essa instancia e vai atualizar logo depois de qualquer modificação aqui feita
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		//restauranteAtual.setAtivo(true);
		restauranteAtual.inativar();
		//não precisa fazer o save porque o JPA ainda está gerenciando essa instancia e vai atualizar logo depois de qualquer modificação aqui feita
	}
	
	@Transactional
	public void ativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::inativar);
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		//restauranteAtual.setAtivo(true);
		restauranteAtual.abrir();
		//não precisa fazer o save porque o JPA ainda está gerenciando essa instancia e vai atualizar logo depois de qualquer modificação aqui feita
	}
	
	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		//restauranteAtual.setAtivo(true);
		restauranteAtual.fechar();
		//não precisa fazer o save porque o JPA ainda está gerenciando essa instancia e vai atualizar logo depois de qualquer modificação aqui feita
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId,Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.getFormasPagamento().remove(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId,Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.getFormasPagamento().add(formaPagamento);
	}
	
	@Transactional
	public void desassociarUsuario(Long restauranteId,Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		restaurante.getUsuarios().remove(usuario);
	}
	
	@Transactional
	public void associarUsuario(Long restauranteId,Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		restaurante.getUsuarios().add(usuario);
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
