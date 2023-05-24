package com.algaworks.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

	public CidadeModelAssemblerV2() {
		super(CidadeControllerV2.class, CidadeModelV2.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksV2 algaLinks;
	
	@Override
	public CidadeModelV2 toModel(Cidade cidade) {
	    CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);
	    
	    modelMapper.map(cidade, cidadeModel);
	    
	   cidadeModel.add(algaLinks.linkToCidades("cidades"));
	    	    
	    return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities)
				.add(
						WebMvcLinkBuilder.linkTo(CidadeControllerV2.class)
						.withSelfRel()
					);
	}
	
}
