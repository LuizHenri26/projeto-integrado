package br.com.lhlibrarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lhlibrarymanagement.model.HistoricoEmprestimoDevolucao;

@Repository
public interface HistoricoEmprestimoDevolucaoRepository extends JpaRepository<HistoricoEmprestimoDevolucao, Long> {

}
