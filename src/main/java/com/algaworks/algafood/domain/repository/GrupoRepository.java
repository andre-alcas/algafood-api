package com.algaworks.algafood.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
//List<Grupo> listar();
//Grupo consultarId(Long id);
//Grupo adicionar(Grupo grupo);
//void remover(Long id);