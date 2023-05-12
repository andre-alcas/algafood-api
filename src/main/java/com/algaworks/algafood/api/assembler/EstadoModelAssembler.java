package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public EstadoModelAssembler() {
		super(EstadoController.class, EstadoModel.class);
	}
	
	@Override
	public EstadoModel toModel(Estado estado) {
		
		EstadoModel estadoModel = createModelWithId(estado.getId(), estado);

		estadoModel.add(algaLinks.linkToEstados("estados"));
		
		return estadoModel;
	}
	 @Override
	    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
	        return super.toCollectionModel(entities)
	            .add(algaLinks.linkToEstados());
	    } 

//	public List<EstadoModel> toCollectionModel(List<Estado> estados) {
//		return estados.stream()
//				.map(estado -> toModel(estado))
//				.collect(Collectors.toList());
//	}
}
