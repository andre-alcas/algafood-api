package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@Getter
//@Setter
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	//@NotNull //aceita espaço em branco
	//@NotEmpty //não aceita 1 espaço em branco, mas aceita vários
	//não aceita 1 espaço em branco e nem aceita vários
	//@NotBlank(groups = {Groups.CadastroRestaurante.class})
	@NotBlank//(message =  "Nome é obrigatório")
	@Column(nullable = false)
	private String nome;
	
	//@DecimalMin("1")
	@NotNull
	@PositiveOrZero
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	//@JsonIgnore
	@Valid
	//@NotNull(groups = {Groups.CadastroRestaurante.class})
	@NotNull
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@ManyToOne//(fetch = FetchType.LAZY) //estrategia eager loading
	@JoinColumn(name= "cozinha_id",nullable = false) //forma de nomear a chave estrangeira criada. o padrão é cozinha_id
	private Cozinha cozinha;
	
	@JsonIgnore
	@Embedded //essa classe está incoporada em outro lugar e sendo incorporada pra dentro de restaurante
	private Endereco endereco;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	@ManyToMany //por padrao usa Lazy Loading
	@JoinTable(name = "restaurante_forma_pagamento", 
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
}
