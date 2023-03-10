package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@GetMapping
	public List<GrupoModel> listar() {
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}

	@GetMapping(value = "/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		return grupoModelAssembler.toModel(cadastroGrupo.buscarOuFalhar(grupoId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public GrupoModel adicionar(@RequestBody  @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoInputDisassembler.toDomainModel(grupoInput);
		return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupo));
	}

	@PutMapping(value = "/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {// instancia de grupo com
																						// propriedades vinda do corpo
																						// da requisi????o
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);

		//BeanUtils.copyProperties(grupo, grupoAtual, "id");
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

		return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupoAtual));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{grupoId}")
	public void remover(@PathVariable Long grupoId) {
	cadastroGrupo.excluir(grupoId);
	}
}
