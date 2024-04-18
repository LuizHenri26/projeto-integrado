package br.com.lhlibrarymanagement.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import br.com.lhlibrarymanagement.enums.StatusENUM;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@DynamicUpdate
@Data
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_livro")
	private Long id;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 100)
	private String titulo;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 14)
	private String isbn;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 65)
	private String autor;
	@NotBlank(message = "Campo obrigatório.")
	@Column(nullable = false, length = 65)
	private String editora;
	@Enumerated(EnumType.STRING)
	private StatusENUM status;
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;
	@ManyToOne
	@JoinColumn(name = "id_idioma")
	private Idioma idioma;
	@ManyToOne
	@JoinColumn(name = "id_setor")
	private Setor setor;
	@OneToMany(mappedBy = "livro")
	private List<Emprestimo> emprestimos;
}
