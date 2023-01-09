//package com.algaworks.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import com.algaworks.algafood.domain.model.Cidade;
//import com.algaworks.algafood.domain.repository.CidadeRepository;
//
//@Component
//public class CidadeRepositoryImpl implements CidadeRepository {
//
//	@PersistenceContext
//	private EntityManager manager;
//	
//	@Override
//	public List<Cidade> listar(){
//		
//		TypedQuery<Cidade> query = manager.createQuery("from Cidade",Cidade.class);
//		
//		return query.getResultList();
//		
//	}
//	
//	@Override
//	public Cidade consultarId(Long id) {
//		return manager.find(Cidade.class, id);//select from where id == id
//	}
//	
//	@Override
//	@Transactional
//	public Cidade adicionar(Cidade cidade) {
//		
//		return manager.merge(cidade);//retorna a instancia persistida
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long id) {
//		Cidade cidade = consultarId(id);
//		if(cidade == null) {
//			throw new EmptyResultDataAccessException(1);
//		}
//		manager.remove(cidade);
//	}
//
//}
