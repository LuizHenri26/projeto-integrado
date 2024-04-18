package br.com.lhlibrarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Membro;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {

	@Query(value = "select * from membro me where not exists (select * from emprestimo e where e.id_membro = me.id_membro and e.status = 'EMPRESTADO')", nativeQuery = true)
	List<Membro> findAllMembros();

	@Query(value = "select count(*) from membro me where exists (select * from emprestimo e where e.id_membro = :id and e.status = 'EMPRESTADO')", nativeQuery = true)
	int quantidadeDeEmprestimoVigenteMembroEspecifico(@Param("id") Long id);
	
	int countByCpf(String cpf);

	List<Membro> findByNomeContainingIgnoreCase(String nome);

	Membro findByCpf(String cpf);

	List<Membro> findAllByOrderByIdAsc();
}
