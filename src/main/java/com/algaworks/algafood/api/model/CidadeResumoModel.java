package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CidadeResumoModel {
	//Cidade DTO

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Ananindeua")
	private String nome;

	@ApiModelProperty(example = "Pará")
	private String estado;

}
