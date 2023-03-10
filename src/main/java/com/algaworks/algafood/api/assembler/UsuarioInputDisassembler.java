package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainModel(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInput usuarioInput,Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
	
//	public void copyToDomainObjectWithouPassword(UsuarioComSenhaInput usuarioSemSenhaInput,Usuario usuario) {
//		modelMapper.map(usuarioSemSenhaInput, usuario);
//	}
//	
//	public void copyToDomainObjectPassword(SenhaInput usuarioSenhaInput,Usuario usuario) {
//		modelMapper.map(usuarioSenhaInput, usuario);
//	}
}
