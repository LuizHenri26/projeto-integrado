package br.com.lhlibrarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByLogin(String login);

	List<Usuario> findByNomeContainingIgnoreCase(String nome);

	@Query(value = "select u from Usuario u order by u.id asc")
	List<Usuario> findAll();
	
	
	Integer countByLogin(String login);

}
