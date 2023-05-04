package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInput {

	
		@ApiModelProperty(example="Comidas Regionais", required=true)
		@NotBlank
		private String nome;
		
		@ApiModelProperty(example="10.00", required=true)
		@NotNull
		@PositiveOrZero
		private BigDecimal taxaFrete;
		
		@Valid
		@NotNull
		private CozinhaIdInput cozinha;
	
		@Valid
		@NotNull
		private EnderecoInput endereco;
}
