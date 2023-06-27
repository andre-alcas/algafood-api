package com.algaworks.algafood.core.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.httpBasic().and().formLogin(); //padrao ter formLogin
		http
		.authorizeRequests()
			.anyRequest().authenticated()
		.and()
		.cors().and()
		.oauth2ResourceServer().jwt();//.opaqueToken(); //configurando um resource server no projeto
			
	}
	
//	@Bean //para chave secreta simetrica
//	public JwtDecoder jwtDecoder() {
//		
//		var secretKey = new SecretKeySpec("algaworksalgaworksalgaworksalgaworksalgaworksalgaworks".getBytes(), "HmacSHA256");
//		
//		return NimbusJwtDecoder.withSecretKey(secretKey).build();
//	}
	

}
