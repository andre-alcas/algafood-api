package com.algaworks.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private List<String> tipo;

	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.tipo =  Arrays.asList(constraintAnnotation.allowed());
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || this.tipo.contains(value.getContentType());
	}//value.getSize() é retornado em Bytes entao tem que comparar com Bytes do maxSize;

}
