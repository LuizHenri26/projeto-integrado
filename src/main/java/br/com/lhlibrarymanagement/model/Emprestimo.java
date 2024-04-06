package br.com.lhlibrarymanagement.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Emprestimo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataEmprestimo;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataPrevista;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataDevolucao;
	@ManyToOne
	@JoinColumn(name = "id_livro")
	private Livro livro;
	@ManyToOne
	@JoinColumn(name = "id_membro")
	private Membro membro;
}
