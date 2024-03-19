package br.com.lhlibrarymanagement.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lhlibrarymanagement.model.Membro;

public interface MembroRepository extends JpaRepository<Membro, Long> {

}
