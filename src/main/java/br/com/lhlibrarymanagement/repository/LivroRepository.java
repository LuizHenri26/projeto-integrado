package br.com.lhlibrarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

	List<Livro> findByTituloContainingIgnoreCase(String titulo);
	
	Livro findByIsbn(String isbn);
	
	@Query("select l from Livro l where l.status = 'DISPONIVEL'")
	List<Livro> findAllStatusDisponivel();
	
	@Query("select l from Livro l order by l.id asc")
	List<Livro> findAll();
}
