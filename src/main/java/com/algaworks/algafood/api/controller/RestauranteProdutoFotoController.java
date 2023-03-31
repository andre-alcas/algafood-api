package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());
		
		return fotoProdutoModelAssembler.toModel(fotoSalva);
//		var nomeArquivo = UUID.randomUUID().toString() 
//				+ "_" + fotoProdutoInput.getArquivo().getOriginalFilename();
//		
//		var arquivoFoto = Path.of("/Users/alcas/catalogo", nomeArquivo);
//		
//		//System.out.println(fotoProdutoInput.getDescricao());
//		//System.out.println(arquivoFoto);
//		//System.out.println(fotoProdutoInput.getArquivo().getContentType());
//		
//		try {
//			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
		
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel recuperarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId) {
		System.out.println("1111111");
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		return fotoProdutoModelAssembler.toModel(fotoProduto);
		
	}
	
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId) {
		System.out.println("aquiiiiiiiiii");
		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
			
			InputStream inputStream = fotoStorage.recuperar(fotoProduto.getNomeArquivo());
			
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(inputStream));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
}