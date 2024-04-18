package br.com.lhlibrarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

}
