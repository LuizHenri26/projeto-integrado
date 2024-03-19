package br.com.lhlibrarymanagement.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lhlibrarymanagement.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

}
