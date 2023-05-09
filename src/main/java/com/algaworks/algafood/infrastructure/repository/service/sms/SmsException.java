package com.algaworks.algafood.infrastructure.repository.service.sms;

public class SmsException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public SmsException(String message, Throwable cause) {
		super(message, cause);
	}

	public SmsException(String message) {
		super(message);
	}

}
