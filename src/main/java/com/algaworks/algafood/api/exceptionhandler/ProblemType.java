package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	DADOS_INVALIDOS("/dados-invalidos", "Dados Inválidos"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado","Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso","Entidade em uso"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incrompreensivel","Mensagem incrompreensivel"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
	ERRO_NEGOCIO("/erro-negocio","Violação de regra de negocio");

	private String title;
	private String uri;

	private ProblemType(String path, String title){
		this.uri = "https://localhost:8080" + path;
		this.title = title;
	}

}
