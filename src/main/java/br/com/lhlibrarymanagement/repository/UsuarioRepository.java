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

	@Query(value = "select * from usuario u where u.nome like %:nome% and u.id <> 1", nativeQuery = true)
	List<Usuario> findByNomeContainingIgnoreCase(@Param("nome") String nome);

	@Query(value = "select u from Usuario u where u.login <> 'admin' order by u.id asc")
	List<Usuario> findAll();

	Integer countByLogin(String login);

}
