package com.algaworks.algafood.api.v1.model;


import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> {
	//Restaurante DTO

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Restaurante Comida Caseira")
	private String nome;

}
