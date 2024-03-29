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

import com.algaworks.algafood.api.ResourceUrlHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
//@RequestMapping(path="/cidades")
@RequestMapping(path="/v1/cidades")//, produces = MediaType.APPLICATION_JSON_VALUE) nao funciona aqui com spring fox v3, tem q colocar em cada metodo
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@CheckSecurity.Cidades.PodeConsultar
	//@Deprecated //metodo para depreciar essa funcao da API
	@Override
	//@GetMapping(produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeModel> listar(){
		
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	@CheckSecurity.Cidades.PodeConsultar
	@Override
	//@GetMapping(path = "/{cidadeId}", produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
	@GetMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		return cidadeModelAssembler.toModel(cidade);
	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@ResponseStatus(HttpStatus.CREATED)
	//@PostMapping(produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainModel(cidadeInput);

			cidade = cadastroCidade.salvar(cidade);

			CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

			ResourceUrlHelper.addUriInResponseHeader(cidadeModel.getId());

			return cidadeModel;
		}catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(),e);
		}
	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	//@PutMapping(path = "/{cidadeId}", produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
	@PutMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeModel atualizar(@PathVariable Long cidadeId,
			@RequestBody @Valid CidadeInput cidadeInput) {//instancia de cidade com propriedades vinda do corpo da requisição

		try {
			Cidade cidadeAtual =  cadastroCidade.buscarOuFalhar(cidadeId);
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			//BeanUtils.copyProperties(cidade, cidadeAtual,"id");
			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		}catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(),e);
		}

	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{cidadeId}")
	public void remover(@PathVariable Long cidadeId) {//instancia de cidade com propriedades vinda do corpo da requisição
		cadastroCidade.excluir(cidadeId);
	}
}
