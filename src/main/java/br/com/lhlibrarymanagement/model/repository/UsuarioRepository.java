package br.com.lhlibrarymanagement.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.lhlibrarymanagement.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByLogin(String login);
	
	List<Usuario> findByNomeContainingIgnoreCase(String nome);
	
	List<Usuario> findByLoginContainingIgnoreCase(String login);
	
	List<Usuario> findByNomeAndLoginContainingIgnoreCase(String nome, String login);

}
