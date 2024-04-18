package br.com.lhlibrarymanagement.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@DynamicUpdate
@Data
public class Membro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_membro")
	private Long id;
	@NotBlank(message = "Campo obrigatório!")
	@Column(nullable = false, length = 65)
	private String nome;
	@NotBlank(message = "Campo obrigatório!")
	@Column(nullable = false, length = 10)
	private String dataNascimento;
	@NotBlank(message = "Campo obrigatório!")
	@Column(nullable = false, length = 14)
	private String cpf;
	@Embedded
	@NotNull
	@Valid
	private Contato contato;
	@Embedded
	@NotNull
	@Valid
	private Endereco endereco;
	@OneToMany(mappedBy = "membro")
	private List<Emprestimo> emprestimos;

}
