package br.com.lhlibrarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Categoria;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.model.Setor;
import br.com.lhlibrarymanagement.model.repository.CategoriaRepository;
import br.com.lhlibrarymanagement.model.repository.LivroRepository;
import br.com.lhlibrarymanagement.model.repository.SetorRepository;

@Service
public class LivroService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private SetorRepository setorRepository;

	/**
	 * Lista todos os registros de tabela categoria.
	 * 
	 * @return - registros da tabela categoria
	 */
	public List<Categoria> getCategorias() {
		Iterable<Categoria> categorias = categoriaRepository.findAll();
		return Streamable.of(categorias).toList();
	}

	/**
	 * Lista todos os setores da tabela setor.
	 * 
	 * @return - registros da tabela setor
	 */
	public List<Setor> getSetores() {
		Iterable<Setor> setores = setorRepository.findAll();
		return Streamable.of(setores).toList();
	}

	public List<Livro> getListarLivros() {
		List<Livro> livros = livroRepository.findAll();
		return livros;
	}

	public void cadastraLivro(Livro livro) {
		livroRepository.save(livro);
	}

	public Optional<Livro> findById(Long id) {
		return livroRepository.findById(id);
	}

	public void deletarLivro(Livro livro) {
		livroRepository.delete(livro);
	}

	public List<Livro> filtrarLivros(String titulo, String autor) {

		List<Livro> livro = null;

		if (isFiltroTituloDoLivroAutorPreenchidos(titulo, autor)) {
			livro = livroRepository.findByTituloAndAutorContainingIgnoreCase(titulo, autor);
		} else {
			if (!titulo.isBlank()) {
				livro = livroRepository.findByTituloContainingIgnoreCase(titulo);
			}
			if (!autor.isBlank()) {
				livro = livroRepository.findByTituloContainingIgnoreCase(titulo);
			}
		}

		return livro;
	}

	public boolean isFiltroTituloDoLivroAutorPreenchidos(String titulo, String autor) {
		return !titulo.isBlank() && !autor.isBlank();
	}
	
	public Livro findByIsbn(String isbn) {
		return livroRepository.findByIsbn(isbn);
	}

}
