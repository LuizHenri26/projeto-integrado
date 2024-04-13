package br.com.lhlibrarymanagement.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.lhlibrarymanagement.enums.StatusENUM;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class HistoricoEmprestimoDevolucao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_historico_emprestimo_devolucao")
	private Long id;
	private Long idEmprestimo;
	private Long idLivro;
	private Long idMembro;
	private String tituloLivro;
	private String nomeMembro;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataEmprestimo;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataPrevista;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataDevolucao;
	@Enumerated(EnumType.STRING)
	private StatusENUM status;

	public HistoricoEmprestimoDevolucao() {
		this.dataEmprestimo = LocalDate.now();
		this.dataPrevista = LocalDate.now().plusDays(14);
	}
}
