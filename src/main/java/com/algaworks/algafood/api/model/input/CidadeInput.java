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
public class CidadeInput {

	
	    @ApiModelProperty(example="Ananindeua",required=true)
		@NotBlank
		private String nome;
		
		@Valid
		@NotNull
		private EstadoIdInput estado;
}
