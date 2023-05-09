package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModel {
	//FotoProduto DTO

	@ApiModelProperty(example = "FotoPizza")
	private String nomeArquivo;

	@ApiModelProperty(example = "Descrição Foto da Pizza Caseira")
	private String descricao;

	@ApiModelProperty(example = "image/jpeg")
	private String contentType;

	@ApiModelProperty(example = "1024")
	private Long tamanho;

}
