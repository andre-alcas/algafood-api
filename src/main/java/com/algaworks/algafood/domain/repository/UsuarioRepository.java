package com.algaworks.algafood.domain.repository;


import java.util.Optional;

import com.algaworks.algafood.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
}
//List<Usuario> listar();
//Usuario consultarId(Long id);
//Usuario adicionar(Usuario usuario);
//Usuario atualizar e alterar senha
//void remover(Long id);