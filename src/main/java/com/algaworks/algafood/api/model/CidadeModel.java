package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CidadeModel {
	//Cidade DTO
	
	private Long id;
	
	private String nome;
	
	private EstadoModel estado;

}
