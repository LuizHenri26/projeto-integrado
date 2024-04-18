package br.com.lhlibrarymanagement.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Embeddable
@Data
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
}
