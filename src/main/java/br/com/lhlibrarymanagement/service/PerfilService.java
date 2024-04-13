package br.com.lhlibrarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Perfil;
import br.com.lhlibrarymanagement.repository.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;

	public Perfil buscarPerfilPorId(Long id) {
		Optional<Perfil> opt = perfilRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IllegalArgumentException("Perfil com id : " + id + " inexistente");
		}
	}

	public Perfil buscarPapel(String nome) {
		Perfil perfil = perfilRepository.findByNome(nome);
		return perfil;
	}

	public List<Perfil> listarPerfil() {
		Iterable<Perfil> perfis = perfilRepository.findAll();
		return Streamable.of(perfis).toList();
	}

}
