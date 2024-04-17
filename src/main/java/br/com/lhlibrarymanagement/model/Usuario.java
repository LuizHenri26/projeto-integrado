package br.com.lhlibrarymanagement.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Campo obrigatório!")
	@Column(nullable = false, length = 65)
	private String nome;
	@NotBlank(message = "Campo obrigatório!")
	@Column(nullable = false, length = 65)
	private String login;
	@NotBlank(message = "Campo obrigatório!")
	@Column(nullable = false)
	private String senha;
	@ManyToOne
	@JoinColumn(name = "id_perfil")
	private Perfil perfil;

}
