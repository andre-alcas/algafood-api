package com.algaworks.algafood.infrastructure.repository.service.storage;

public class StorageException extends RuntimeException {

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageException(String message) {
		super(message);
	}

}
