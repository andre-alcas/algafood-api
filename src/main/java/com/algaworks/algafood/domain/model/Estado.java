package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

//@Getter
//@Setter
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table (name="tab_cozinhas") //2a opção
public class Estado {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;

}
