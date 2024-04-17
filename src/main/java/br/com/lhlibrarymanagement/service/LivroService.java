package br.com.lhlibrarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Categoria;
import br.com.lhlibrarymanagement.model.Idioma;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.model.Setor;
import br.com.lhlibrarymanagement.repository.CategoriaRepository;
import br.com.lhlibrarymanagement.repository.IdiomaRepository;
import br.com.lhlibrarymanagement.repository.LivroRepository;
import br.com.lhlibrarymanagement.repository.SetorRepository;

@Service
public class LivroService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private SetorRepository setorRepository;

	@Autowired
	private IdiomaRepository idiomaRepository;

	public List<Categoria> getCategorias() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return Streamable.of(categorias).toList();
	}

	public List<Setor> getSetores() {
		List<Setor> setores = setorRepository.findAll();
		return Streamable.of(setores).toList();
	}

	public List<Idioma> getIdiomas() {
		List<Idioma> idiomas = idiomaRepository.findAll();
		return Streamable.of(idiomas).toList();
	}

	public List<Livro> getListarLivros() {
		List<Livro> livros = livroRepository.findAll();
		return  Streamable.of(livros).toList();
	}

	public Livro findById(Long id) {
		Optional<Livro> opt = livroRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IllegalArgumentException("Livro com id : " + id + " não existe.");
		}
	}

	public List<Livro> filtrarLivroPeloTitulo(String titulo) {
		List<Livro> livro = livroRepository.findByTituloContainingIgnoreCase(titulo);
		return livro;
	}

	public Livro findByIsbn(String isbn) {
		return livroRepository.findByIsbn(isbn);
	}
	
	public Integer countIsbnExistente(String nome) {
		Integer quantidadeIsbnExistente = livroRepository.countByIsbn(nome);
		return quantidadeIsbnExistente;
	}

	public void cadastraLivro(Livro livro) {
		livroRepository.save(livro);
	}

	public void deletarLivro(Livro livro) {
		livroRepository.delete(livro);
	}

	public void editarLivro(Livro livro, final Long id) {
		Livro livros = findById(id);
		// Mantém o status do livro
		livro.setStatus(livros.getStatus());
		cadastraLivro(livro);
	}

	public boolean isIsbnExistente(final Long id, final Livro livro) {
		Livro livroIsbn = findById(id);
		Integer countIsbnExistente = countIsbnExistente(livro.getIsbn());
		String isbnAtual = livroIsbn.getIsbn();
		String isbnFuturo = livro.getIsbn();
		return !isbnAtual.equals(isbnFuturo) && countIsbnExistente > 0;
	}

}
