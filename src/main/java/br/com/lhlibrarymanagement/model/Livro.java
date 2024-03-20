package br.com.lhlibrarymanagement.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Livro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "O campo titulo do livro é obrigatório.")
	private String titulo;
	@NotBlank(message = "O campo ISBN do livro é obrigatório.")
	private String isbn;
	@NotBlank(message = "O campo nome do autor é obrigatório.")
	private String autor;
	@NotNull(message = "O campo numero de paginas é obrigatório.")
	private Integer numeroPaginas;
	@NotBlank(message = "O campo nome da editora é obrigatório.")
	private String editora;
	private String status;
	@NotNull(message = "O campo categoria é obrigatório.")
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;
	@OneToMany(mappedBy = "livro")
	private List<Emprestimo> emprestimos;
	@NotNull(message = "O campo setor é obrigatório.")
	@ManyToOne
	@JoinColumn(name="id_setor")
	private Setor setor;


}
