package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController //@Controller //@ResponseBody
//@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping//(produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} ) //retorna jason e xml, mas dá pra escolher o que produzir(formato)
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	@GetMapping(value = "/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElse(null);
		if(restaurante!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(restaurante);
		}
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {//instancia de restaurante com propriedades vinda do corpo da requisição
		
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	
	@PutMapping(value = "/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {//instancia de restaurante com propriedades vinda do corpo da requisição
		
		try {
			Restaurante restauranteAtual = restauranteRepository
					.findById(restauranteId).orElse(null);
			
			if(restauranteAtual!=null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual,
						"id","formasPagamento","endereco","dataCadastro");//ignorar copia de Id e formas de pagamento
			
				restauranteAtual = cadastroRestaurante.salvar(restauranteAtual);
				//return ResponseEntity.status(HttpStatus.CREATED).body(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
				
			}
			return ResponseEntity.notFound().build();
		}
			catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = "/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {//instancia de restaurante com propriedades vinda do corpo da requisição
		try {
			cadastroRestaurante.excluir(restauranteId);
			return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}catch(EntidadeEmUsoException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	@PatchMapping(value = "/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object>campos){
		
		Restaurante restauranteAtual = restauranteRepository
				.findById(restauranteId).orElse(null);
		
		if(restauranteAtual ==  null) {
			return ResponseEntity.notFound().build();
		}
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteId,restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade,valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			//System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
	
	/*
	    @GetMapping(value = "/{restauranteId}")
	    public Restaurante buscar(@PathVariable Long restauranteId) {
		return restauranteRepository.consultarId(restauranteId);
	}
	 */
	/*
	//@ResponseStatus(HttpStatus.OK) //dá pra alterar a status da resposta do http aqui
	@GetMapping(value = "/{restauranteId}")
	//public Restaurante buscar(@PathVariable("restauranteId") Long id) {
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteRepository.consultarId(restauranteId);
		//return restauranteRepository.consultarId(restauranteId);
		//return  ResponseEntity.status(HttpStatus.OK).body(restaurante);
		//return ResponseEntity.ok(restaurante);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION,"http://localhost:8080/restaurantes");
		return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
	}
	*/
	

}
