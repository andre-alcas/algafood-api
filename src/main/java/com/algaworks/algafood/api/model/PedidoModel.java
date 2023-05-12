package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel>{
	//Pedido DTO

    //private Long id;
	@ApiModelProperty(example = "ASXCTRF")
	private String codigo;

	@ApiModelProperty(example = "100.99")
    private BigDecimal subtotal;

	@ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

	@ApiModelProperty(example = "110.99")
    private BigDecimal valorTotal;

	@ApiModelProperty(example = "CONFIRMADO")
    private String status;

	@ApiModelProperty(example = "2023-05-03T23:18:06Z")
    private OffsetDateTime dataCriacao;

	@ApiModelProperty(example = "2023-05-03T23:18:06Z")
    private OffsetDateTime dataConfirmacao;

	@ApiModelProperty(example = "2023-05-03T23:18:06Z")
    private OffsetDateTime dataEntrega;

	@ApiModelProperty(example = "2023-05-03T23:18:06Z")
    private OffsetDateTime dataCancelamento;


    //private RestauranteResumoModel restaurante;
	private RestauranteApenasNomeModel restaurante;

    private UsuarioModel cliente;


    private FormaPagamentoModel formaPagamento;


    private EnderecoModel enderecoEntrega;


    private List<ItemPedidoModel> itens;

}
