package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


//@ApiModel(value="Cidade", description = "Representa uma cidade")
@Relation(collectionRelation = "cidades")//muda nome da colletion list pra cidades
@Getter
@Setter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {
	//Cidade DTO

	//@ApiModelProperty(value="ID da cidade",example="1")
	@ApiModelProperty(example="1")
	private Long id;

	@ApiModelProperty(example="Ananindeua")
	private String nome;

	//private EstadoModel estado;

}
