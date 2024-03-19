package br.com.lhlibrarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Categoria;
import br.com.lhlibrarymanagement.model.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	
	/**
	 * Buscar categorias pelo nome
	 */
	public List<Categoria> filtrarPeloNome(String nome) {
		return categoriaRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	/**
	 * Lista todos os registros tabela categoria.
	 * @return - registros da tabela categoria
	 */ 
	public List<Categoria> getCategorias(){
		Iterable<Categoria> categorias =  categoriaRepository.findAll();
		return Streamable.of(categorias).toList();
	}
	
	/**
	 * Insere dados na tabela categoria
	 * @param categoria - entidade categoria
	 */
	public void cadastrarCategoria(Categoria categoria) {
		categoriaRepository.save(categoria);
	}
	
	/**
	 * Deleta os dados da tabela
	 * @param categoria - entidade categoria
	 */
	public void deletarCategoria(Categoria categoria) {
		categoriaRepository.delete(categoria);
	}
	
	/**
	 * Busca a categoria pelo identificador
	 * @param id - identificador da categoria.
	 * @return - a categoria especifica
	 */
	public Optional<Categoria> findById(Long id) {
		return categoriaRepository.findById(id);
	}
}
