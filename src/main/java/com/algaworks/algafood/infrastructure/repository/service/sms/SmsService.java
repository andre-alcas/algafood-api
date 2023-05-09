package com.algaworks.algafood.infrastructure.repository.service.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.core.sms.SmsProperties;
import com.algaworks.algafood.domain.service.EnvioSmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService implements EnvioSmsService {

	@Autowired
	private SmsProperties smsProperties;

	@Override
	public void enviar(SMS sms) {
		try {
			 Twilio.init(smsProperties.getSid(), smsProperties.getToken());
		     Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(sms.getFrom()), sms.getCorpo() ).create();

		} catch (Exception e) {
			throw new SmsException("Não foi possível enviar sms",e);
		}

	}

}
