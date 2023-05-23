package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {
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
