package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

		@Query("select max(dataAtualizacao) from FormaPagamento")
		OffsetDateTime getDataUltimaAtualizacao();
		
		FormaPagamento findFirstByOrderByDataAtualizacaoDesc();
		
		@Query("select dataAtualizacao from FormaPagamento where id = :formaPagamentoId")
		OffsetDateTime getDataAtualizacaoById(Long formaPagamentoId);  
}
