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
//import com.algaworks.algafood.domain.model.FormaPagamento;
//import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
//
//@Component
//public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {
//
//	@PersistenceContext
//	private EntityManager manager;
//
//	@Override
//	public List<FormaPagamento> listarformaspagamento(){
//
//		TypedQuery<FormaPagamento> query = manager.createQuery("from FormaPagamento",FormaPagamento.class);
//
//		return query.getResultList();
//
//	}
//
//	@Override
//	public FormaPagamento consultarId(Long id) {
//		return manager.find(FormaPagamento.class, id);//select from where id == id
//	}
//
//	@Override
//	@Transactional
//	public FormaPagamento adicionar(FormaPagamento formapagamento) {
//
//		return manager.merge(formapagamento);//retorna a instancia persistida
//	}
//
//	@Override
//	@Transactional
//	public void remover(FormaPagamento formapagamento) {
//		formapagamento = consultarId(formapagamento.getId());
//		manager.remove(formapagamento);
//	}
//
//}