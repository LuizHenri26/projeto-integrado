package br.com.lhlibrarymanagement.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lhlibrarymanagement.model.Membro;

public interface MembroRepository extends JpaRepository<Membro, Long> {
	
	@Query(value = "select * from membro me where me.id not in (select e.id_membro from emprestimo e)", nativeQuery = true)
	List<Membro> findAllMembrosNaoPegaramLivrosEmprestados();

}
