package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


//@ApiModel(value="Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeModel {
	//Cidade DTO
	
	//@ApiModelProperty(value="ID da cidade",example="1")
	@ApiModelProperty(example="1")
	private Long id;
	
	@ApiModelProperty(example="Ananindeua")
	private String nome;
	
	private EstadoModel estado;

}
