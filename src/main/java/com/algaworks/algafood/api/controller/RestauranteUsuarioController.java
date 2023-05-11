package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController // @Controller //@ResponseBody
//@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController implements RestauranteUsuarioControllerOpenApi {


	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@Override
	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		return usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios());
	}

	@Override
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId ) {
		cadastroRestaurante.desassociarUsuario(restauranteId, usuarioId);
	}

	@Override
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId ) {
		cadastroRestaurante.associarUsuario(restauranteId, usuarioId);
	}

}
