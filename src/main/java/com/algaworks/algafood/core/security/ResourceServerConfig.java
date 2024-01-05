package com.algaworks.algafood.core.security;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.httpBasic().and().formLogin(); //padrao ter formLogin
		http
		//.authorizeRequests()
			//.antMatchers(HttpMethod.POST, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
			//.antMatchers(HttpMethod.PUT, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
			//.antMatchers(HttpMethod.GET, "/v1/cozinhas/**").authenticated()
			//.anyRequest().denyAll()
		//.and()
		.csrf().disable()
		.cors().and()
		.oauth2ResourceServer().jwt()//.opaqueToken(); //configurando um resource server no projeto
		.jwtAuthenticationConverter(jwtAuthenticationConverter());
			
	}
	
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			var authorities = jwt.getClaimAsStringList("authorities");
			
			if (authorities == null) {
				authorities = Collections.emptyList();
			}
			
			return authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		});
		
		return jwtAuthenticationConverter;
	}
	
//	@Bean //para chave secreta simetrica
//	public JwtDecoder jwtDecoder() {
//		
//		var secretKey = new SecretKeySpec("algaworksalgaworksalgaworksalgaworksalgaworksalgaworks".getBytes(), "HmacSHA256");
//		
//		return NimbusJwtDecoder.withSecretKey(secretKey).build();
//	}
	

}
