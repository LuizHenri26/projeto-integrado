package br.com.lhlibrarymanagement.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.lhlibrarymanagement.enums.StatusENUM;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Emprestimo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_emprestimo")
	private Long id;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataEmprestimo;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataPrevista;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataDevolucao;
	@Enumerated(EnumType.STRING)
	private StatusENUM status;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_livro")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NotNull
	private Livro livro;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_membro")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NotNull
	private Membro membro;

	public Emprestimo() {
		this.dataEmprestimo = LocalDate.now();
		this.dataPrevista = LocalDate.now().plusDays(14);
	}
}
