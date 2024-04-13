package br.com.lhlibrarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	List<Categoria> findByNomeContainingIgnoreCase(String nome);
	
	@Query("select c from Categoria c order by c.id asc")
	List<Categoria> findAllCategoriaOrdenadaPeloId();
	
	Categoria findByNome(String nome);
	
	@Query(value = "select ct.id, ct.nome from categoria ct", nativeQuery = true)
	List<Categoria> findAll();
}
