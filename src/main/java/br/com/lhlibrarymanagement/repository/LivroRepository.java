package br.com.lhlibrarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.enums.StatusENUM;
import br.com.lhlibrarymanagement.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

	List<Livro> findByTituloContainingIgnoreCase(String titulo);
	
	Livro findByIsbn(String isbn);
	
	List<Livro> findAllByStatus(@Param("status") StatusENUM status);
	
	int countByIsbn(String isbn);

	List<Livro> findAllByOrderByIdAsc();
}
