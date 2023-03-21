package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{
	
	//List<Cozinha> findTodasByNomeContaining(String nome);//nome é uma propriedade declarada em Cozinha
	Page<Cozinha> findTodasByNomeContaining(String nome, Pageable pageable);//nome é uma propriedade declarada em Cozinha
	//usar prefixo findBy(propriedade) ou somente propriedade ou
	//usar prefixo findQUALQUERCOISABy(propriedade)
	
	Optional<Cozinha> findByNome(String nome);
	
	boolean existsByNome(String nome);
	
}
	/*
	List<Cozinha> listar();
	List<Cozinha> consultarPorNome(String nome);
	Cozinha consultarId(Long id);
	Cozinha adicionar(Cozinha cozinha);
	void remover(Long id);
	 */
