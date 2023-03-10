package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;

	@GetMapping
	public List<UsuarioModel> listar() {
		return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
	}

	@GetMapping(value = "/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		return usuarioModelAssembler.toModel(cadastroUsuario.buscarOuFalhar(usuarioId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public UsuarioModel adicionar(@RequestBody  @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
		Usuario usuario = usuarioInputDisassembler.toDomainModel(usuarioComSenhaInput);
		return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
	}

	@PutMapping(value = "/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput  usuarioInput) {// instancia de usuario com
																						// propriedades vinda do corpo
																						// da requisi????o
		Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

		return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
	}
	
	@PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
        cadastroUsuario.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }
//		}
//		try {
//			
//			if(usuarioSenhaInput.getSenhaAtual() == usuarioAtual.getSenha()) {
//				usuarioInputDisassembler.copyToDomainObjectPassword(usuarioSenhaInput, usuarioAtual);
//			}
//		}catch (Exception e) {
//			
//		}
		
	

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{usuarioId}")
	public void remover(@PathVariable Long usuarioId) {
	cadastroUsuario.excluir(usuarioId);
	}
}
