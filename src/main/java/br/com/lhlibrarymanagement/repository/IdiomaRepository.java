package br.com.lhlibrarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Idioma;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long>{

}
