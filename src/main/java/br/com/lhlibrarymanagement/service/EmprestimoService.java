package br.com.lhlibrarymanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
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
		Iterable<Membro> membros = this.membroRepository.findAllMembros();
		return Streamable.of(membros).toList();
	}

	public List<Livro> getLivrosDisponiveis() {
		Iterable<Livro> livros = this.livroRepository.findAllStatusDisponivel();
		return Streamable.of(livros).toList();
	}

	public List<Emprestimo> getListarEmprestimosVigentes() {
		Iterable<Emprestimo> emprestimos = this.emprestimoRepository.findAllEmprestimosVigentes();
		return Streamable.of(emprestimos).toList();
	}

	public List<Emprestimo> filtrarMembros(String nome) {
		Iterable<Emprestimo> emprestimos = this.emprestimoRepository.findFiltrarMembro(nome);
		return Streamable.of(emprestimos).toList();
	}

	public Emprestimo findById(final Long id) {
		Optional<Emprestimo> opt = this.emprestimoRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IllegalArgumentException("Emprestimo com id : " + id + " não existe.");
		}
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