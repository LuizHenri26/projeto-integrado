package br.com.lhlibrarymanagement.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Contato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 15)
	private String numeroContato;
	@Email
	@Column(nullable = false, length = 65)
	private String email;
	@OneToOne(mappedBy = "contato")
	private Membro membro;

}
