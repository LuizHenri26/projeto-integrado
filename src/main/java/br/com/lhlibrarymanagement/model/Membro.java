package br.com.lhlibrarymanagement.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
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
	@NotNull(message = "Campo obrigatório!")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	@NotBlank(message = "Campo obrigatório!")
	@Column(nullable = false, length = 14)
	private String cpf;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_contato")
	@NotNull
	@Valid
	private Contato contato;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_endereco")
	@NotNull
	@Valid
	private Endereco endereco;
	@OneToMany(mappedBy = "membro")
	private List<Emprestimo> emprestimos;

}
