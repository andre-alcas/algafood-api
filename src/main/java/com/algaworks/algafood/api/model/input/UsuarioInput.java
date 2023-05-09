package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

	@ApiModelProperty(example = "André Siqueira", required = true)
	@NotBlank
	private String nome;

	@ApiModelProperty(example = "contato@email.com.br", required = true)
	@NotBlank
	@Email
	private String email;

}
