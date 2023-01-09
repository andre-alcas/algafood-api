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
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.findAll();
	}

	@GetMapping(value = "/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
		Estado estado = estadoRepository.findById(estadoId).orElse(null);
		if(estado!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(estado);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public Estado adicionar(@RequestBody Estado estado) {
		return cadastroEstado.salvar(estado);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {//instancia de estado com propriedades vinda do corpo da requisição
		Estado estadoAtual = estadoRepository.findById(estadoId).orElse(null);

		if(estadoAtual != null) {
			//estadoAtual.setNome(estado.getNome());
			BeanUtils.copyProperties(estado, estadoAtual,"id");//ignorar copia de Id
			Estado estadoSalvo = cadastroEstado.salvar(estadoAtual);
			//return ResponseEntity.status(HttpStatus.OK).body(estado);
			return ResponseEntity.ok(estadoSalvo);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value = "/{estadoId}")
	public ResponseEntity<Estado> remover(@PathVariable Long estadoId) {//instancia de estado com propriedades vinda do corpo da requisição
		try {
			cadastroEstado.excluir(estadoId);
			return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}catch(EntidadeEmUsoException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
