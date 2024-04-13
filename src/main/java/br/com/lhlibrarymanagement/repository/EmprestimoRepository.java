package br.com.lhlibrarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

	@Query("select e from Emprestimo e where e.status = 'EMPRESTADO' order by e.id asc")
	List<Emprestimo> findAllEmprestimosVigentes();

	@Query(value = "select e.id_emprestimo, e.data_devolucao, e.data_emprestimo, e.data_prevista, \n"
			+ " e.status as status_emprestimo, e.id_livro as livro, e.id_membro as membro, l.*, me.* \n"
			+ " from emprestimo e "
			+ " left join membro me on me.id_membro = e.id_membro left join livro l on l.id_livro = e.id_livro \n"
			+ " where me.nome like %:nome% ", nativeQuery = true)
	List<Emprestimo> findFiltrarMembro(@Param("nome") String nome);
}
