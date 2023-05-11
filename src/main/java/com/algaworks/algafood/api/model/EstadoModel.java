package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "estados")//muda nome da colletion list pra estados
@Getter
@Setter
public class EstadoModel extends RepresentationModel<EstadoModel>{
	//Estado DTO

	@ApiModelProperty(example="1",required=true)
	private Long id;

	@ApiModelProperty(example="Pará")
	private String nome;


}
