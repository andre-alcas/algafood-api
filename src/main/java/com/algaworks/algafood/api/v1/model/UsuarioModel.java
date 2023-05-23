package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")//muda nome da colletion list pra usuarios
@Getter
@Setter
public class UsuarioModel extends RepresentationModel<UsuarioModel> {
	//Grupo DTO

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Andre Siqueira")
	private String nome;

	@ApiModelProperty(example = "usuario@gmail.com")
	private String email;


}
