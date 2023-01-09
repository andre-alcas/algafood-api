package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //essa interface não deve ser levada em conta pra afim de instaciação de um repositorio pelo spring data jpa 
public interface CustomJpaRepository<T,ID>  extends JpaRepository<T,ID>{
	
		Optional<T> buscarPrimeiro();

}
