package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //criador de construtor da classe
public class Problema {
	private LocalDateTime datahora;
	private String mensagem;
}
