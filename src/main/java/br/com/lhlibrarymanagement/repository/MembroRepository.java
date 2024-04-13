package br.com.lhlibrarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Membro;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {

	@Query(value = "select * from membro me where me.id_membro not in (select e.id_membro from emprestimo e) or not exists (select * from emprestimo e where e.id_membro = me.id_membro and e.status = 'EMPRESTADO')", nativeQuery = true)
	List<Membro> findAllMembros();

	@Query(value = "select count(*) from membro me where me.id_membro = :id and exists (select * from emprestimo e where e.id_membro = me.id_membro and e.status = 'EMPRESTADO')", nativeQuery = true)
	Integer quantidadeDeEmprestimoVigenteMembroEspecifico(@Param("id") Long id);

	List<Membro> findByNomeContainingIgnoreCase(String nome);

	Membro findByCpf(String cpf);

	@Query("select me from Membro me order by me.id asc")
	List<Membro> findAll();
}
