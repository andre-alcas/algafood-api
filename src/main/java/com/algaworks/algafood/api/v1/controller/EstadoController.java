package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
//@RequestMapping("/estados")
@RequestMapping(path="/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@Override
	@GetMapping
	public CollectionModel<EstadoModel> listar() {
		List<Estado> todosEstados = estadoRepository.findAll();
		return estadoModelAssembler.toCollectionModel(todosEstados);
	}

	@Override
	@GetMapping(value = "/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
		return estadoModelAssembler.toModel(estado);
	}

	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public EstadoModel adicionar(@RequestBody  @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainModel(estadoInput);
		return estadoModelAssembler.toModel(cadastroEstado.salvar(estado));
	}

	@Override
	@PutMapping(value = "/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {// instancia de estado com
																						// propriedades vinda do corpo
																						// da requisição
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

		//BeanUtils.copyProperties(estado, estadoAtual, "id");
		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

		return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));
	}

	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{estadoId}")
	public void remover(@PathVariable Long estadoId) {
	cadastroEstado.excluir(estadoId);
	}
}
