package br.com.lhlibrarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.enums.StatusENUM;
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
		return categoriaRepository.findAll();
	}

	public List<Setor> getSetores() {
		return setorRepository.findAll();
	}

	public List<Idioma> getIdiomas() {
		return idiomaRepository.findAll();
	}

	public List<Livro> getListarLivros() {
		return livroRepository.findAllByOrderByIdAsc();
	}

	public Livro findById(Long id) {
		return livroRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Livro com id : " + id + " não existe."));
	}

	public List<Livro> filtrarLivroPeloTitulo(String titulo) {
		return livroRepository.findByTituloContainingIgnoreCase(titulo);
	}

	public Livro findByIsbn(String isbn) {
		return livroRepository.findByIsbn(isbn);
	}

	public int countIsbnExistente(String nome) {
		return livroRepository.countByIsbn(nome);
	}

	public void cadastraLivro(Livro livro) {
		livro.setStatus(StatusENUM.DISPONIVEL);
		livroRepository.save(livro);
	}

	public void deletarLivro(Livro livro) {
		livroRepository.delete(livro);
	}

	public void editarLivro(Livro livro, final Long id) {
		Livro livros = findById(id);
		// Mantém o status do livro
		livro.setStatus(livros.getStatus());
		livroRepository.save(livro);
	}

	public boolean isIsbnExistente(final Long id, final Livro livro) {
		int countIsbnExistente = livroRepository.countByCpfAndId(livro.getIsbn(), id);
		return countIsbnExistente > 0;
	}

}
