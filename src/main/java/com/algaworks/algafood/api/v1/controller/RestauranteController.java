package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

//@CrossOrigin(origins= {"http://localhost:8000"})
@RestController // @Controller //@ResponseBody
//@RequestMapping("/restaurantes")
@RequestMapping(path = "/v1/restaurantes")//, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

//	@Autowired
//	private SmartValidator validator;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required=false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
//
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//		if("apenas-nome".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		}else if("completo".equals(projecao)){
//			restaurantesWrapper.setSerializationView(null);
//		}
//
//		return restaurantesWrapper;
//	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<RestauranteBasicoModel> listar() {
		return restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
//	@Override
//	@JsonView(RestauranteView.Resumo.class)
//	@GetMapping
//	public List<RestauranteModel> listar() {
//		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
//	}

//
//	@JsonView(RestauranteView.Resumo.class)
//	@GetMapping(params= "projecao=resumo")
//	public List<RestauranteModel> listarResumido() {
//		return listar();
//	}
//
	/*
	 * como não é possivel anotar usando JsonView em CollectionModel entao foi
	 * necessario refatorar os metodos
	 */
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
		return restauranteApenasNomeModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
//	@Override
//	@ApiOperation(value = "Lista restaurantes", hidden = true)
//	@JsonView(RestauranteView.ApenasNome.class)
//	@GetMapping(params= "projecao=apenas-nome")
//	public List<RestauranteModel> listarApenasNomes() {
//		return listar();
//	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(value = "/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		return restauranteModelAssembler.toModel(restaurante);
		// return cadastroRestaurante.buscarOuFalhar(restauranteId);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {// instancia de
																								// restaurante com
																								// propriedades vinda do
																								// corpo da requisição

		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainModel(restauranteInput);
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping(value = "/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {

		try {
			// Restaurante restaurante =
			// restauranteInputDisassembler.toDomainModel(restauranteInput);
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

//			BeanUtils.copyProperties(restaurante, restauranteAtual,
//					"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
			// substitui o BeanUtils.copyProperties
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));

		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	// PUT /restaurantes/{id}/ativo para ativar
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping(value = "/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	// DELETE /restaurantes/{id}/ativo para inativar
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@DeleteMapping(value = "/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	// PUT /restaurantes/{id}/fechamento
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping(value = "/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abertura(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);

		return ResponseEntity.noContent().build();
	}

	// PUT /restaurantes/{id}/fechamento
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping(value = "/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechamento(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);

		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping(value = "/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMuitos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@DeleteMapping(value = "/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMuitos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	@DeleteMapping(value = "/{restauranteId}")
//	public void remover(@PathVariable Long restauranteId) {
//		cadastroRestaurante.excluir(restauranteId);
//	}

//	@PatchMapping(value = "/{restauranteId}")
//	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
//			@RequestBody Map<String, Object> campos,HttpServletRequest request) {
//
//		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
//
//		merge(campos, restauranteAtual,request);
//		validate(restauranteAtual,"restaurante");
//
//		return atualizar(restauranteId, restauranteAtual);
//	}
//
//	private void validate(Restaurante restaurante, String objectName) {
//		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//
//		validator.validate(restaurante, bindingResult);
//
//		if(bindingResult.hasErrors()) {
//			throw new ValidacaoException(bindingResult);
//		}
//   }
//
//	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
//			HttpServletRequest request) {
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//
//			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//				field.setAccessible(true);
//
//				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//
//				ReflectionUtils.setField(field, restauranteDestino, novoValor);
//			});
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}

}
