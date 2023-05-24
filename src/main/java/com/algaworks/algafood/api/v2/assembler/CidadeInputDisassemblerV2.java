package com.algaworks.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassemblerV2 {

	@Autowired
	private ModelMapper modelMapper;

	public Cidade toDomainModel(CidadeInputV2 cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}

	public void copyToDomainObject(CidadeInputV2 cidadeInput,Cidade cidade) {
		 //evita a seguinte exception:
	    //Caused by: org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		cidade.setEstado(new Estado());//serve pra ajudar a referenciar uma nova cozinha dentro de cidade e evitar um erro no jpa em Atualizar
		modelMapper.map(cidadeInput, cidade);
	}
}
