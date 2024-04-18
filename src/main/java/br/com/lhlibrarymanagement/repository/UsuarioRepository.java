package br.com.lhlibrarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByLogin(String login);

	List<Usuario> findByNomeContainingIgnoreCase(String nome);

	int countByLogin(String login);
	
	@Query(value = "select count(u) from Usuario u where u.login = :login and u.id <> :id")
	int countByLoginAndId(@Param("login") String login, @Param("id") Long id);
}
