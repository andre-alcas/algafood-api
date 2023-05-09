package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {
	//Produto DTO

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Pizza 4 queijos")
	private String nome;

	@ApiModelProperty(example = "Mussarela, Gorgonzola, Parmessão e Cheddar")
	private String descricao;

	@ApiModelProperty(example = "52.30")
	private BigDecimal preco;

	@ApiModelProperty(example = "true")
	private Boolean ativo;

}
