package com.algaworks.algafood.api.controller;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}

	@GetMapping(value = "/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cidadeRepository.findById(cidadeId).orElse(null);
		if(cidade != null) {
			return ResponseEntity.status(HttpStatus.OK).body(cidade);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public Cidade adicionar(@RequestBody Cidade cidade) {
		return cadastroCidade.salvar(cidade);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {//instancia de cidade com propriedades vinda do corpo da requisição
		try {
			Cidade cidadeAtual = cidadeRepository.findById(cidadeId).orElse(null);

			if(cidadeAtual != null) {
				//cidadeAtual.setNome(cidade.getNome());
				BeanUtils.copyProperties(cidade, cidadeAtual,"id");//ignorar copia de Id
				Cidade cidadeSalva = cadastroCidade.salvar(cidadeAtual);
				return ResponseEntity.ok(cidadeSalva);
			}
			return ResponseEntity.notFound().build();
		} catch(EntidadeNaoEncontradaException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = "/{cidadeId}")
	public ResponseEntity<Cidade> remover(@PathVariable Long cidadeId) {//instancia de cidade com propriedades vinda do corpo da requisição
		try {
			cadastroCidade.excluir(cidadeId);
			return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}catch(EntidadeEmUsoException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
