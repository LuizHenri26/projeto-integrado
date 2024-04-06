package br.com.lhlibrarymanagement.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long>{
	
	@Query(value = "select s.id, s.nome from setor s", nativeQuery = true)
	List<Setor> findAll();

}
