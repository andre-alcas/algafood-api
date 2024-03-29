package com.algaworks.algafood.domain.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.Pedido;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

	//@Query("from Pedido where codigo =: codigo") //JPA já faz isso, não precisa
	Optional<Pedido> findByCodigo(String codigo);

	@Override
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();
	
	boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);

}