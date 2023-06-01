package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
		.oauth2ResourceServer().opaqueToken(); //configurando um resource server no projeto
			
	
	}
	

}
