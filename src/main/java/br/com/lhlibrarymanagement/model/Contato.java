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
public class Contato implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = true, length = 30)
	private String numeroTelefone;
	@Column(nullable = true, length = 30)
	private String numeroCelular;
	@Column(nullable = true, length = 65)
	private String email;
	@OneToOne(mappedBy = "contato")
	private Membro membro;
	
}
