package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


//@JsonFilter("pedidoFilter")//tem que usar obrigatoriamente na controller
@Getter
@Setter
public class PedidoResumoModel {
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
    
	
    private RestauranteResumoModel restaurante;
    //private UsuarioModel cliente;
    
	@ApiModelProperty(example = "Andre Siqueira")
    private String nomeCliente;

}
