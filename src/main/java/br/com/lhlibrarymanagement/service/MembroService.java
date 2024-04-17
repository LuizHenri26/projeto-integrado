package br.com.lhlibrarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Membro;
import br.com.lhlibrarymanagement.repository.MembroRepository;

@Service
public class MembroService {

	@Autowired
	private MembroRepository membroRepository;

	public Integer countEmprestimoVigenteMembro(Long id) {
		Integer quantidadeEmprestimoVigenteMembro = membroRepository.quantidadeDeEmprestimoVigenteMembroEspecifico(id);
		return quantidadeEmprestimoVigenteMembro;
	}

	public Integer countCpfExistente(String cpf) {
		Integer countCpfExistente = membroRepository.countByCpf(cpf);
		return countCpfExistente;
	}

	/**
	 * Realiza a consulta de todos os membros.
	 * 
	 * @return - membros cadastrados no sistema.
	 */
	public List<Membro> getMembros() {
		List<Membro> membros = membroRepository.findAll();
		return Streamable.of(membros).toList();
	}

	/**
	 * Realiza a consulta do membro pelo seu nome.
	 * 
	 * @param nome - nome do membro
	 * @return - membro especifico consultado pelo nome.
	 */
	public List<Membro> filtrarMembroPeloNome(String nome) {
		List<Membro> membros = membroRepository.findByNomeContainingIgnoreCase(nome);
		return Streamable.of(membros).toList();
	}

	/**
	 * Realiza a consulta do membro pelo identificador.
	 * 
	 * @param id - id do membro.
	 * @return - membro pelo id.
	 */
	public Membro findById(Long id) {
		Optional<Membro> opt = membroRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IllegalArgumentException("Membro com id : " + id + " não existe.");
		}
	}

	/**
	 * Realiza a consulta do cpf.
	 * 
	 * @param cpf - numero do cpf.
	 * @return - cpf do membro.
	 */
	public Membro findByCpf(String cpf) {
		Membro membro = membroRepository.findByCpf(cpf);
		return membro;
	}

	/**
	 * Realiza a inserção de membros no sistema.
	 * 
	 * @param membro - entidade membro.
	 */
	public void cadastrarMembro(Membro membro) {
		membroRepository.save(membro);
	}

	/**
	 * Realiza a deleção do membro,
	 * 
	 * @param membro - entidade membro.
	 */
	public void deletarMembro(Membro membro) {
		membroRepository.delete(membro);
	}

	/**
	 * Valida se o cpf é existente.
	 * 
	 * @param id  - identificador do membro.
	 * @param cpf - nuemro do cpf.
	 * @return - {true} se o cpf existir, {false} cpf não existe.
	 */
	public boolean isCpfExistente(Long id, Membro membro) {
		Membro membroCpf = findById(id);
		Integer countCpfExistente = countCpfExistente(membro.getCpf());
		String cpfAtual = membroCpf.getCpf();
		String cpfFuturo = membro.getCpf();
		return !cpfAtual.equals(cpfFuturo) && countCpfExistente > 0;
	}

}
