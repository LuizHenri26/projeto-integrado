package br.com.lhlibrarymanagement.service;

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
		Iterable<Membro> membros = membroRepository.findAll();
		return Streamable.of(membros).toList();
	}
	
	public List<Livro> getLivro() {
		Iterable<Livro> livros = livroRepository.findAll();
		return Streamable.of(livros).toList();
	}
	
	public void registraEmprestimo(Emprestimo emprestimo) {
		emprestimoRepository.save(emprestimo);
	}


}
