package br.com.lhlibrarymanagement.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String isbn;
	private String autor;
	private Integer numeroPaginas;
	private String editora;
	private String status;
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;
	@OneToMany(mappedBy = "livro")
	private List<Emprestimo> emprestimos;
	@OneToMany(mappedBy = "livro")
	private List<Setor> setor;

}
