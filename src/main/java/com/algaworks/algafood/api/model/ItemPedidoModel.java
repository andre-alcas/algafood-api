package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel>{
	//ItemPedido DTO

	@ApiModelProperty(example = "1")
    private Long produtoId;

	@ApiModelProperty(example = "Pizza M")
    private String produtoNome;

	@ApiModelProperty(example = "1")
    private Integer quantidade;

	@ApiModelProperty(example = "33.20")
    private BigDecimal precoUnitario;

	@ApiModelProperty(example = "33.20")
    private BigDecimal precoTotal;

	@ApiModelProperty(example = "sem cebola")
    private String observacao;

}
