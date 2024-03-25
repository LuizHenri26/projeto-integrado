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
	 * Filtra as categorias pelo nome.
	 */
	public List<Categoria> filtrarPeloNome(String nome) {
		return categoriaRepository.findByNomeContainingIgnoreCase(nome);
	}

	/**
	 * Lista todos os registros de tabela categoria de forma ordenada .
	 * 
	 * @return - Os registros da tela categoria.
	 */
	public List<Categoria> getCategorias() {
		Iterable<Categoria> categorias = categoriaRepository.findAllCategoriaOrdenadaPeloId();
		return Streamable.of(categorias).toList();
	}

	public Categoria buscarNomeDaCategoria(String nome) {
		return categoriaRepository.findByNome(nome);
	}

	/**
	 * Insere dados na tabela categoria
	 * 
	 * @param categoria - entidade categoria
	 */
	public void cadastrarCategoria(Categoria categoria) {
		categoriaRepository.save(categoria);
	}

	/**
	 * Deleta os dados da tabela categoria.
	 * 
	 * @param categoria - entidade categoria
	 */
	public void deletarCategoria(Categoria categoria) {
		categoriaRepository.delete(categoria);
	}

	/**
	 * Busca a categoria pelo identificador.
	 * 
	 * @param id - identificador da categoria.
	 * @return - categoria especifica
	 */
	public Optional<Categoria> findById(Long id) {
		return categoriaRepository.findById(id);
	}
	
	

}
