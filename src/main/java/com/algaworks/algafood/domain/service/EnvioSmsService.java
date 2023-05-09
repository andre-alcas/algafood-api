package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public interface EnvioSmsService {

	void enviar(SMS sms);

	@Builder
	@Getter
	class SMS{

		@NonNull
		private String from;

		@NonNull
		private String to;

		@NonNull
		private String corpo;
	}


}
