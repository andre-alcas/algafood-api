package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RestauranteModel {
	//Restaurante DTO
	
	private Long id;
	
	private String nome;
	
	private BigDecimal frete;
	
	private CozinhaModel cozinha;
	//private String nomeCozinha;
	//private Long idCozinha;
	
	private Boolean ativo;
	
	private EnderecoModel endereco;

}
