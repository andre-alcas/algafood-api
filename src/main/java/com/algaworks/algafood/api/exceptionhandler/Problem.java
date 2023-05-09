package com.algaworks.algafood.api.exceptionhandler;


import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder // criador de construtor da classe
public class Problem {

	@ApiModelProperty(example="400")
	private Integer status;

	@ApiModelProperty(example="http://www.exemplo.com/dados-invalidos")
	private String type;

	@ApiModelProperty(example="Dados inválidos")
	private String title;

	@ApiModelProperty(example="Um ou mais campos estão inválidos")
	private String detail;
	// private LocalDateTime datahora;
	// private String mensagem;
	@ApiModelProperty(example="Um ou mais campos estão inválidos")
	private String userMessage;

	@ApiModelProperty(example="2023-05-02T19:56:50.00000Z")
	private OffsetDateTime timestamp;

	@ApiModelProperty(example="Objetos ou campos que geraram o erro(opcional)")
	private List<Object> objects;

	@ApiModel("ObjetoProblema!")
	@Getter
	@Builder
	public static class Object{

		@ApiModelProperty(example="preço")
		private String name;

		@ApiModelProperty(example="O preço é obrigatório")
		private String userMessage;
	}
}
