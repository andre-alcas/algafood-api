package com.algaworks.algafood.api.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder // criador de construtor da classe
public class Problem {
	private Integer status;
	private String type;
	private String title;
	private String detail;
	// private LocalDateTime datahora;
	// private String mensagem;
}
