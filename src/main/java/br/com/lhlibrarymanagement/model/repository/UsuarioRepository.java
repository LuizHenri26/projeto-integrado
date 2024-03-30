package br.com.lhlibrarymanagement.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByLogin(String login);
	
	List<Usuario> findByNomeContainingIgnoreCase(String nome);
	
	List<Usuario> findByLoginContainingIgnoreCase(String login);
	
	List<Usuario> findByNomeAndLoginContainingIgnoreCase(String nome, String login);
	
	//Porque foi inserido via script o usuario do sistema
	@Query(value = "select max(u.id) from usuario u", nativeQuery = true)
	Integer MaxId();
	
	@Query(value = "select * from usuario u where u.login <> 'admin'", nativeQuery = true)
	List<Usuario> findAll();

}
