package br.com.lhlibrarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.enums.StatusENUM;
import br.com.lhlibrarymanagement.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

	List<Emprestimo> findAllByStatusOrderByIdAsc(@Param("status") StatusENUM status);

	@Query(value = "select e from Emprestimo e left join e.membro me left join e.livro l where me.nome LIKE CONCAT('%',:nome,'%') and e.status = :status")
	List<Emprestimo> findFiltrarNomeDoMembro(@Param("nome") String nome, @Param("status") StatusENUM status);
}
