package com.algaworks.algafood.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
//List<Estado> listar();
//Estado consultarId(Long id);
//Estado adicionar(Estado estado);
//void remover(Long id);