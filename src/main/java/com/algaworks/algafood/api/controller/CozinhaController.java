package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController //@Controller //@ResponseBody
@RequestMapping(value = "/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	//@GetMapping//(produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} ) //retorna jason e xml, mas dá pra escolher o que produzir(formato)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<CozinhaModel> listar(@PageableDefault(size=10) Pageable pageable){
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		List<CozinhaModel> cozinhasModel = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());
		Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel,pageable,cozinhasPage.getTotalElements());
		return cozinhasModelPage;
	}
	
	@GetMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);		
		return cozinhaModelAssembler.toModel(cozinha);
		//return cadastroCozinha.buscarOuFalhar(cozinhaId);
	}
//	@GetMapping(value = "/{cozinhaId}")
//	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
//		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElse(null);
//		if(cozinha != null) {
//			return ResponseEntity.status(HttpStatus.OK).body(cozinha);
//		}
//		//return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		return ResponseEntity.notFound().build();
//	}
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {//instancia de cozinha com propriedades vinda do corpo da requisição
		try {
			Cozinha cozinha = cozinhaInputDisassembler.toDomainModel(cozinhaInput);
			return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		//return cadastroCozinha.salvar(cozinha);//retornar no corpo da resposta o recurso
	}

	@PutMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody  @Valid CozinhaInput cozinhaInput) {//instancia de cozinha com propriedades vinda do corpo da requisição
		
		try {
			Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
			
			cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
			
			return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
			
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		
		}
		//Cozinha cozinhaAtual =  cadastroCozinha.buscarOuFalhar(cozinhaId);
		
		//BeanUtils.copyProperties(cozinha, cozinhaAtual,"id");
		
		//return cadastroCozinha.salvar(cozinhaAtual);
	}
//	@ResponseStatus(HttpStatus.OK)
//	@PutMapping(value = "/{cozinhaId}")
//	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {//instancia de cozinha com propriedades vinda do corpo da requisição
//		Cozinha cozinhaAtual = cozinhaRepository.findById(cozinhaId).orElse(null);
//		
//		if(cozinhaAtual != null) {
//			//cozinhaAtual.setNome(cozinha.getNome());
//			BeanUtils.copyProperties(cozinha, cozinhaAtual,"id");//ignorar copia de Id
//			Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual);
//			//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
//			return ResponseEntity.ok(cozinhaSalva);
//		}
//		return ResponseEntity.notFound().build();
//	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{cozinhaId}", produces = {})
	public void remover(@PathVariable Long cozinhaId) {//instancia de cozinha com propriedades vinda do corpo da requisição
			cadastroCozinha.excluir(cozinhaId);
	}
	/*
	 * @ResponseStatus(HttpStatus.OK)
	 * 
	 * @DeleteMapping(value = "/{cozinhaId}") public ResponseEntity<?>
	 * remover(@PathVariable Long cozinhaId) {//instancia de cozinha com
	 * propriedades vinda do corpo da requisição try {
	 * cadastroCozinha.excluir(cozinhaId); return
	 * ResponseEntity.noContent().build(); }catch (EntidadeNaoEncontradaException e)
	 * { return ResponseEntity.notFound().build(); //return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
	 * }catch(EntidadeEmUsoException e){ return
	 * ResponseEntity.status(HttpStatus.CONFLICT).build(); } }
	 * 
	 */	
	/*
	    @GetMapping(value = "/{cozinhaId}")
	    public Cozinha buscar(@PathVariable Long cozinhaId) {
		return cozinhaRepository.consultarId(cozinhaId);
	}
	 */
	/*
	//@ResponseStatus(HttpStatus.OK) //dá pra alterar a status da resposta do http aqui
	@GetMapping(value = "/{cozinhaId}")
	//public Cozinha buscar(@PathVariable("cozinhaId") Long id) {
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.consultarId(cozinhaId);
		//return cozinhaRepository.consultarId(cozinhaId);
		//return  ResponseEntity.status(HttpStatus.OK).body(cozinha);
		//return ResponseEntity.ok(cozinha);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION,"http://localhost:8080/cozinhas");
		return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
	}
	*/

}
