package br.com.lhlibrarymanagement.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Embeddable
@Data
public class Contato implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 15)
	private String numeroContato;
	@Email
	@Column(nullable = false, length = 65)
	private String email;

}
