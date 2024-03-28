package br.com.lhlibrarymanagement.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lhlibrarymanagement.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

	List<Livro> findByTituloContainingIgnoreCase(String titulo);
	Livro findByIsbn(String isbn);
	
	@Query("select l from Livro l where l.status = 'Disponivel'")
	List<Livro> findAllStatusDisponivel();
}
