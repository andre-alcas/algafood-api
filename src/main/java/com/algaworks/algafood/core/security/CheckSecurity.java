package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {
	
	public @interface Cozinhas{
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
		public @interface PodeEditar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		public @interface PodeConsultar {}
		
	}
	
	public @interface Restaurantes {
		
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeGerenciarCadastro { }
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
	    		+ "hasAuthority('EDITAR_RESTAURANTES') or"
	    		+ "@algaSecurity.gerenciaRestaurante(#restauranteId)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeGerenciarFuncionamento { }

//	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
//	    @Retention(RUNTIME)
//	    @Target(METHOD)
//	    public @interface PodeEditar { }

	    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
//	public @interface Pedidos {
//		
//		@PreAuthorize("hasAuthority('SCOPE_READ') and "
//				+ "hasAuthority('CONSULTAR_PEDIDOS') or"
//				+ "@algaSecurity.clienteDoPedido(#codigoPedido) or"
//				+ "@algaSecurity.gerenciaRestauranteDoPedido(#codigoPedido)")
//	    @Retention(RUNTIME)
//	    @Target(METHOD)
//	    public @interface PodeBuscar { }
//	    
//	}
	
	public @interface Pedidos {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or"
				     + "@algaSecurity.getUsuarioId == returnObject.cliente.id or"
				     + "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeBuscar { }
	    
	}

}
