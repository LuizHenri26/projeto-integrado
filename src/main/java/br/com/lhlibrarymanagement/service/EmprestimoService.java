package br.com.lhlibrarymanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Emprestimo;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.model.Membro;
import br.com.lhlibrarymanagement.model.repository.EmprestimoRepository;
import br.com.lhlibrarymanagement.model.repository.LivroRepository;
import br.com.lhlibrarymanagement.model.repository.MembroRepository;

@Service
public class EmprestimoService {
	
	@Autowired
	private MembroRepository membroRepository;
	
	@Autowired 
	private LivroRepository livroRepository;
	
	@Autowired
	EmprestimoRepository emprestimoRepository;
	
	
	public List<Membro> getMembros() {
		Iterable<Membro> membros = membroRepository.findAllMembrosNaoPegaramLivrosEmprestados();
		return Streamable.of(membros).toList();
	}
	
	public List<Livro> getLivro() {
		Iterable<Livro> livros = livroRepository.findAllStatusDisponivel();
		return Streamable.of(livros).toList();
	}
	
	public void registrarEmprestimo(Emprestimo emprestimo) {
		emprestimo.setDataEmprestimo(LocalDate.now());
		emprestimo.setDataPrevista(LocalDate.now().plusDays(14));
		emprestimo.getLivro().setStatus("Indisponivel");
		emprestimoRepository.save(emprestimo);
	}


}
