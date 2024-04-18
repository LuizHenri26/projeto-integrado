package br.com.lhlibrarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Contato;
import br.com.lhlibrarymanagement.model.Endereco;
import br.com.lhlibrarymanagement.model.Membro;
import br.com.lhlibrarymanagement.repository.MembroRepository;

@Service
public class MembroService {

	@Autowired
	private MembroRepository membroRepository;

	/**
	 * Realiza a consulta de todos os membros.
	 * 
	 * @return - membros cadastrados no sistema.
	 */
	public List<Membro> getMembros() {
		return membroRepository.findAllByOrderByIdAsc();
	}

	/**
	 * Realiza a consulta do membro pelo seu nome.
	 * 
	 * @param nome - nome do membro
	 * @return - membro especifico consultado pelo nome.
	 */
	public List<Membro> filtrarMembroPeloNome(String nome) {
		return membroRepository.findByNomeContainingIgnoreCase(nome);
	}

	/**
	 * Realiza a consulta do membro pelo identificador.
	 * 
	 * @param id - id do membro.
	 * @return - membro pelo id.
	 */
	public Membro findById(Long id) {
		return membroRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Membro com id : " + id + " não existe."));
	}

	/**
	 * Realiza a consulta do cpf.
	 * 
	 * @param cpf - numero do cpf.
	 * @return - cpf do membro.
	 */
	public Membro findByCpf(String cpf) {
		return membroRepository.findByCpf(cpf);
	}

	public int countEmprestimoVigenteMembro(Long id) {
		return membroRepository.quantidadeDeEmprestimoVigenteMembroEspecifico(id);
	}

	/**
	 * Realiza a consulta da quantidade de cpf cadastrado com esse numero
	 * 
	 * @param cpf - numero do cpf
	 * @return - quantidade de cpf com numero existente.
	 */
	public int countCpfExistente(String cpf) {
		return membroRepository.countByCpf(cpf);
	}

	/**
	 * Realiza a inserção de membros no sistema.
	 * 
	 * @param membro - entidade membro.
	 */
	public void cadastrarMembro(Membro membro) {
		membroRepository.save(membro);
	}

	public void editararMembro(Membro membro, Endereco endereco, Contato contato) {
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
		int countCpfExistente = membroRepository.countByCpfAndId(membro.getCpf(), id);
		return countCpfExistente > 0;
	}

}
