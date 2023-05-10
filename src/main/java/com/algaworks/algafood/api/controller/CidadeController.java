package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path="/cidades")
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeModel> listar(){
		
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		List<CidadeModel> cidadesModel = cidadeModelAssembler.toCollectionModel(todasCidades);
		
		 CollectionModel<CidadeModel> cidadesCollectionModel = CollectionModel.of(cidadesModel);
		
		 cidadesModel.forEach(cidadeModel -> {
			 cidadeModel.add(
						WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
								.buscar(cidadeModel.getId())
								)
						.withSelfRel()
						);
				cidadeModel.add(
						WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
								.listar()
								)
						.withSelfRel()
						);
				cidadeModel.add(
						WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
								.buscar(cidadeModel.getEstado().getId())
								)
						.withSelfRel()
						);
		 });//FIM FOREACH
		 
		 cidadesCollectionModel.add(
					WebMvcLinkBuilder.linkTo(CidadeController.class)
					.withSelfRel()
					);
		 
		return cidadesCollectionModel;
	}

	@Override
	@GetMapping(value = "/{cidadeId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
		
		cidadeModel.add(
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
						.buscar(cidadeModel.getId())
						)
				.withSelfRel()
				);
		
//		cidadeModel.add(
//				WebMvcLinkBuilder.linkTo(CidadeController.class)
//				.slash(cidadeModel.getId()).withSelfRel()
//				);

		//cidadeModel.add(Link.of("http://localhost:8080/cidades/1"));
//		cidadeModel.add(Link.of("http://localhost:8080/cidades/1", IanaLinkRelations.SELF));

//		cidadeModel.add(Link.of("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));
		//cidadeModel.add(Link.of("http://localhost:8080/cidades", "cidades"));
//		cidadeModel.add(
//				WebMvcLinkBuilder.linkTo(CidadeController.class)
//				.withSelfRel()
//				);
		
		cidadeModel.add(
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
						.listar()
						)
				.withSelfRel()
				);

		//cidadeModel.getEstado().add(Link.of("http://localhost:8080/estados/1"));
//		cidadeModel.add(
//				WebMvcLinkBuilder.linkTo(EstadoController.class)
//				.slash(cidadeModel.getEstado().getId()).withSelfRel()
//				);
		cidadeModel.add(
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
						.buscar(cidadeModel.getEstado().getId())
						)
				.withSelfRel()
				);

		
		return cidadeModel;
	}

	@Override
	@ResponseStatus(HttpStatus.CREATED)
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

	@Override
	@PutMapping(value = "/{cidadeId}",produces = MediaType.APPLICATION_JSON_VALUE)
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

	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{cidadeId}")
	public void remover(@PathVariable Long cidadeId) {//instancia de cidade com propriedades vinda do corpo da requisição
		cadastroCidade.excluir(cidadeId);
	}
}
