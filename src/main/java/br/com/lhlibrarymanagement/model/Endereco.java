package br.com.lhlibrarymanagement.model;

import java.io.Serializable;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@DynamicUpdate
@Data
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 65)
	private String logradouro;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 20)
	private String numero;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 10)
	private String cep;
	@Column(nullable = false, length = 20)
	private String complemento;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 65)
	private String cidade;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 65)
	private String estado;
	@OneToOne(mappedBy = "endereco")
	private Membro membro;
}
