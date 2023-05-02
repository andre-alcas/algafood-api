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

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@ApiOperation(value="Lista as cidades")
	@GetMapping
	public List<CidadeModel> listar(){
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code=400,message="ID da cidade inválido",response = Problem.class),
		@ApiResponse(code=404,message="Cidade não encontrada",response = Problem.class),
	})
	@GetMapping(value = "/{cidadeId}")
	public CidadeModel buscar(@ApiParam(value="ID de uma cidade", example="1") @PathVariable Long cidadeId) {
		return cidadeModelAssembler.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
	}
	
	@ApiOperation("Adiciona uma nova cidade")
	@ApiResponses({
		@ApiResponse(code=201,message="Cidade cadastrada"),
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public CidadeModel adicionar(@ApiParam(name="corpo",value="Representação de uma nova cidade") @RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainModel(cidadeInput);
			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
		}catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(),e);
		}
	}
	
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code=200,message="Cidade atualizada"),
		@ApiResponse(code=404,message="Cidade não encontrada",response = Problem.class),
	})
	@PutMapping(value = "/{cidadeId}")
	public CidadeModel atualizar(@ApiParam(value="ID de uma cidade", example="1") @PathVariable Long cidadeId,
			@ApiParam(name="corpo",value="Representação de uma nova cidade")
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
	
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code=204,message="Cidade excluída"),
		@ApiResponse(code=404,message="Cidade não encontrada",response = Problem.class),
	})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{cidadeId}")
	public void remover(@ApiParam(value="ID de uma cidade", example="1") @PathVariable Long cidadeId) {//instancia de cidade com propriedades vinda do corpo da requisição
		cadastroCidade.excluir(cidadeId);
	}
}
