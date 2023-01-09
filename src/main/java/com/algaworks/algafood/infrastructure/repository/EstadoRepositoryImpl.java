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
//import com.algaworks.algafood.domain.model.Estado;
//import com.algaworks.algafood.domain.repository.EstadoRepository;
//
//@Component
//public class EstadoRepositoryImpl implements EstadoRepository {
//
//	@PersistenceContext
//	private EntityManager manager;
//	
//	@Override
//	public List<Estado> listar(){
//		
//		TypedQuery<Estado> query = manager.createQuery("from Estado",Estado.class);
//		
//		return query.getResultList();
//		
//	}
//	
//	@Override
//	public Estado consultarId(Long id) {
//		return manager.find(Estado.class, id);//select from where id == id
//	}
//	
//	@Override
//	@Transactional
//	public Estado adicionar(Estado estado) {
//		
//		return manager.merge(estado);//retorna a instancia persistida
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long id) {
//		Estado estado = consultarId(id);
//		if(estado == null) {
//			throw new EmptyResultDataAccessException(1);
//		}
//		manager.remove(estado);
//	}
//
//}
