package com.algaworks.algafood.domain.repository;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository
	extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
	JpaSpecificationExecutor<Restaurante> {

	@Override
	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	//usar prefixo findBy(propriedade) ou somente propriedade ou
	//usar prefixo findQUALQUERCOISABy(propriedade)
	//query find get

	//List<Restaurante> findByNomeContainingAndCozinhaId(String Nome, Long cozinha);

	//@Query("from Restaurante where nome like %:nome% and cozinha_id = :id")
	//Está dentro de orm.xml
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

	List<Restaurante> findTop2ByNomeContaining(String Nome);

	int countByCozinhaId(Long Cozinha);

}
//List<Restaurante> listar();
//Restaurante consultarId(Long id);
//Restaurante adicionar(Restaurante restaurante);
//void remover(Long id);