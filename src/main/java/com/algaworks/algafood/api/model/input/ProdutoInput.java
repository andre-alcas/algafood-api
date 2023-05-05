package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {
	
	    @ApiModelProperty(example = "Pizza Caseira",required = true)
		@NotBlank
		private String nome;
		
	    @ApiModelProperty(example = "Pizza com queijo, presunto e manjericão",required = true)
		@NotBlank
		private String descricao;
		
	    @ApiModelProperty(example = "47.80",required = true)
		@NotNull
		@PositiveOrZero
		private BigDecimal preco;
		
	    @ApiModelProperty(example = "false",required = true)
		@NotNull
		private Boolean ativo;
		
}
