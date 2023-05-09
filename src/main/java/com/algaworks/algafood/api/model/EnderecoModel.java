package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EnderecoModel {
	//Endereco DTO

	@ApiModelProperty(example = "66123-000")
	private String cep;

	@ApiModelProperty(example = "Av. Ananin")
	private String logradouro;

	@ApiModelProperty(example = "2")
	private String numero;

	@ApiModelProperty(example = "prox. ao Mercado")
	private String complemento;

	@ApiModelProperty(example = "Centro")
	private String bairro;

	private CidadeResumoModel cidade;

}
