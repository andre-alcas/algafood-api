package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@Getter
//@Setter
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table (name="tab_cozinhas") //2a opção
//@JsonRootName("gastronomia")
public class Cozinha {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	//@JsonProperty(value = "Código da Cozinha")
	//@JsonIgnore
	private Long id;
	
	//@JsonIgnore
	//@JsonProperty(value = "título")
	@Column(nullable = false)
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cozinha") //mapeado em cozinha no Restaurante, vínculo
	private List<Restaurante> restaurantes =  new ArrayList<>();

}
