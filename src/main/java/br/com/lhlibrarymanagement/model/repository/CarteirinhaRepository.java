package br.com.lhlibrarymanagement.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Carteirinha;

@Repository
public interface CarteirinhaRepository extends JpaRepository<Carteirinha, Long>{

}
