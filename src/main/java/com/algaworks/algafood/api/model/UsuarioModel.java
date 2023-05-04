package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {
	//Grupo DTO
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Andre Siqueira")
	private String nome;
	
	@ApiModelProperty(example = "usuario@gmail.com")
	private String email;
	

}
