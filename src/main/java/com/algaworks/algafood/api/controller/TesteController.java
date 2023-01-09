package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController //@Controller //@ResponseBody
//@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
//	@Autowired
//	private CadastroCozinhaService cadastroCozinha;
	
	
	@GetMapping("/cozinhas/por-nome") // (produces=
	// {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} )
	// retorna jason e xml, mas dá pra escolher o que produzir(formato)
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}

	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNome(nome);
	}
	
	@GetMapping("/cozinhas/exists")
	public boolean cozinhaExists(@RequestParam("nome") String nome) {
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/cozinhas/primeira")
	public Optional<Cozinha> cozinhaPrimeiro() {
		return cozinhaRepository.buscarPrimeiro();
	}

	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	//localhost:8080/teste/restaurantes/por-taxa-frete?taxaInicial=1&taxaFinal=20
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorNome(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> restaurantesTop2PorNome(String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	@GetMapping("/restaurantes/count-por-cozinha")
	public int restaurantesCountPorCozinha(Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}
}
