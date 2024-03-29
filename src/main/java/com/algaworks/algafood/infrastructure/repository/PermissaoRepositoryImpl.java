//package com.algaworks.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.algaworks.algafood.domain.model.Permissao;
//import com.algaworks.algafood.domain.repository.PermissaoRepository;
//
//@Component
//public class PermissaoRepositoryImpl implements PermissaoRepository {
//
//	@PersistenceContext
//	private EntityManager manager;
//
//	@Override
//	public List<Permissao> listarpermissoes(){
//
//		TypedQuery<Permissao> query = manager.createQuery("from Permissao",Permissao.class);
//
//		return query.getResultList();
//
//	}
//
//	@Override
//	public Permissao consultarId(Long id) {
//		return manager.find(Permissao.class, id);//select from where id == id
//	}
//
//	@Override
//	@Transactional
//	public Permissao adicionar(Permissao permissao) {
//
//		return manager.merge(permissao);//retorna a instancia persistida
//	}
//
//	@Override
//	@Transactional
//	public void remover(Permissao permissao) {
//		permissao = consultarId(permissao.getId());
//		manager.remove(permissao);
//	}
//
//}
