package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Override
	public CidadeModel toModel(Cidade cidade) {
	    CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
	    
	    modelMapper.map(cidade, cidadeModel);
	    
	    cidadeModel.add(algaLinks.linkToCidades("cidades"));
	    
	    cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
	    
	    return cidadeModel;
	}

//	@Override
//	public CidadeModel toModel(Cidade cidade) {
//		
//		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
//		
//		modelMapper.map(cidade, cidadeModel);//ja faz a relação withSelfRel
//		
//		//CidadeModel cidadeModel = modelMapper.map(cidade, CidadeModel.class);
//		
////		cidadeModel.add(
////				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
////						.buscar(cidadeModel.getId())
////						)
////				.withSelfRel()
////				);
//		
////		cidadeModel.add(
////				WebMvcLinkBuilder.linkTo(CidadeController.class)
////				.slash(cidadeModel.getId()).withSelfRel()
////				);
//
//		//cidadeModel.add(Link.of("http://localhost:8080/cidades/1"));
////		cidadeModel.add(Link.of("http://localhost:8080/cidades/1", IanaLinkRelations.SELF));
//
////		cidadeModel.add(Link.of("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));
//		//cidadeModel.add(Link.of("http://localhost:8080/cidades", "cidades"));
////		cidadeModel.add(
////				WebMvcLinkBuilder.linkTo(CidadeController.class)
////				.withSelfRel()
////				);
//		
//		cidadeModel.add(
//				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
//						.listar()
//						)
//				.withSelfRel()
//				);
//
//		//cidadeModel.getEstado().add(Link.of("http://localhost:8080/estados/1"));
////		cidadeModel.add(
////				WebMvcLinkBuilder.linkTo(EstadoController.class)
////				.slash(cidadeModel.getEstado().getId()).withSelfRel()
////				);
//		cidadeModel.add(
//				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
//						.buscar(cidadeModel.getEstado().getId())
//						)
//				.withSelfRel()
//				);
//		
//		return cidadeModel;
//	}
	
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities)
				.add(
						WebMvcLinkBuilder.linkTo(CidadeController.class)
						.withSelfRel()
					);
	}
	
//	public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
//		return cidades.stream()
//				.map(cidade -> toModel(cidade))
//				.collect(Collectors.toList());
//	}
}
