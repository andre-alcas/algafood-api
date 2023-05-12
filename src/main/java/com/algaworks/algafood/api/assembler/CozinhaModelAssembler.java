package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public CozinhaModelAssembler() {
		super(CozinhaController.class,CozinhaModel.class);
	}
	
	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);

		modelMapper.map(cozinha, cozinhaModel);// ja faz a relação withSelfRel
		
		 cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
		
		return cozinhaModel;
	}

//	public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
//		return cozinhas.stream()
//				.map(cozinha -> toModel(cozinha))
//				.collect(Collectors.toList());
//	}
}
