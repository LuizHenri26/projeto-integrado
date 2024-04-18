package br.com.lhlibrarymanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.enums.StatusENUM;
import br.com.lhlibrarymanagement.model.Emprestimo;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.model.Membro;
import br.com.lhlibrarymanagement.repository.EmprestimoRepository;
import br.com.lhlibrarymanagement.repository.LivroRepository;
import br.com.lhlibrarymanagement.repository.MembroRepository;

@Service
public class EmprestimoService {

	@Autowired
	private MembroRepository membroRepository;

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private EmprestimoRepository emprestimoRepository;

	public List<Membro> getMembros() {
		return membroRepository.findAllMembros();
	}

	public List<Livro> getLivrosDisponiveis() {
		return livroRepository.findAllByStatus(StatusENUM.DISPONIVEL);
	}

	public List<Emprestimo> getListarEmprestimosVigentes() {
		return emprestimoRepository.findAllByStatusOrderByIdAsc(StatusENUM.EMPRESTADO);
	}

	public List<Emprestimo> filtrarMembros(String nome) {
		return emprestimoRepository.findFiltrarNomeDoMembro(nome, StatusENUM.EMPRESTADO);
	}

	public Emprestimo findById(final Long id) {
		return emprestimoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Emprestimo com id : " + id + " não existe."));
	}

	public void registrarEmprestimo(Emprestimo emprestimo) {
		emprestimo.setDataEmprestimo(LocalDate.now());
		emprestimo.setDataPrevista(LocalDate.now().plusDays(14));
		emprestimo.setStatus(StatusENUM.EMPRESTADO);
		emprestimo.getLivro().setStatus(StatusENUM.EMPRESTADO);
		this.emprestimoRepository.save(emprestimo);
	}

	public void registrarDevolucao(Emprestimo emprestimo) {
		emprestimo.setStatus(StatusENUM.DEVOLVIDO);
		emprestimo.getLivro().setStatus(StatusENUM.DISPONIVEL);
		emprestimo.setDataDevolucao(LocalDate.now());
		this.emprestimoRepository.save(emprestimo);
	}

}