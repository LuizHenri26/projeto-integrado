package br.com.lhlibrarymanagement.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Endereco implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = true, length = 65)
	private String logradouro;
	@Column(nullable = true, length = 30)
	private String numero;
	@Column(nullable = true, length = 8)
	private String cep;
	@Column(nullable = true, length = 30)
	private String complemento;
	@Column(nullable = true, length = 65)
	private String cidade;
	@Column(nullable = true, length = 65)
	private String estado;
	@OneToOne(mappedBy = "endereco")
	private Membro membro;
}
