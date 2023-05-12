package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {
	//Restaurante DTO

	//@JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
	private Long id;

	//@JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
	private String nome;

	//@JsonView(RestauranteView.Resumo.class)
	private BigDecimal frete;

	//@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;
	//private String nomeCozinha;
	//private Long idCozinha;

	private Boolean ativo;

	private Boolean aberto;

	private EnderecoModel endereco;

}
