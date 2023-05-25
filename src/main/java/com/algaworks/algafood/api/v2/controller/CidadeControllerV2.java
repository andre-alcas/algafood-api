package com.algaworks.algafood.api.v2.controller;

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
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.core.web.AlgaMediaTypes;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
//@RequestMapping(path="/cidades")
@RequestMapping(path="/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeModelAssemblerV2 cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassemblerV2 cidadeInputDisassembler;

	
	@GetMapping(produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeModelV2> listar(){
		
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	@GetMapping(path = "/{cidadeId}", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
	public CidadeModelV2 buscar(@PathVariable Long cidadeId) {
		
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		return cidadeModelAssembler.toModel(cidade);
	}

	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
	public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainModel(cidadeInput);

			cidade = cadastroCidade.salvar(cidade);

			CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidade);

			ResourceUrlHelper.addUriInResponseHeader(cidadeModel.getIdCidade());

			return cidadeModel;
		}catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(),e);
		}
	}

	
	@PutMapping(path = "/{cidadeId}", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
	public CidadeModelV2 atualizar(@PathVariable Long cidadeId,
			@RequestBody @Valid CidadeInputV2 cidadeInput) {//instancia de cidade com propriedades vinda do corpo da requisição

		try {
			Cidade cidadeAtual =  cadastroCidade.buscarOuFalhar(cidadeId);
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			//BeanUtils.copyProperties(cidade, cidadeAtual,"id");
			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		}catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(),e);
		}

	}

//  Não pode ser mapeado na mesma URL em um MediaType diferente, já que não aceita entrada e retorna void.
//	@DeleteMapping(value = "/{cidadeId}", produces = {})
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void remover(@PathVariable Long cidadeId) {
//		cadastroCidade.excluir(cidadeId);	
//	}
}
