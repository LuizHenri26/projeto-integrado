package br.com.lhlibrarymanagement.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lhlibrarymanagement.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

}
