/*
package com.algaworks.algafood.infrastructure.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Repository
public class CozinhaRepositoryImplOldVersion implements CozinhaRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cozinha> listar(){
		
		TypedQuery<Cozinha> query = manager.createQuery("from Cozinha",Cozinha.class);
		
		return query.getResultList();
		
	}
	
	@Override
	public List<Cozinha> consultarPorNome(String nome){
		return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class)
				.setParameter("nome", "%" + nome + "%")
				.getResultList();
	}
	
	@Override
	public Cozinha consultarId(Long id) {
		return manager.find(Cozinha.class, id);//select from where id == id
	}
	
	@Override
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		
		return manager.merge(cozinha);//retorna a instancia persistida
	}
	
	@Override
	@Transactional
	public void remover(Long id) {
		Cozinha cozinha = consultarId(id);
		if(cozinha == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cozinha);
	}

}
*/