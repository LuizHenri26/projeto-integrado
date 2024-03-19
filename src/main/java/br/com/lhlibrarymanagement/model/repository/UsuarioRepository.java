package br.com.lhlibrarymanagement.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lhlibrarymanagement.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
