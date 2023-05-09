package com.algaworks.algafood.core.sms;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Setter
@Getter
@Component
@ConfigurationProperties("algafood.sms")
public class SmsProperties {

	@NotNull
	private String sid;

	@NotNull
	private String token;

}