package br.com.lhlibrarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Perfil;
import br.com.lhlibrarymanagement.repository.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;

	public List<Perfil> listarPerfil() {
		List<Perfil> perfis = perfilRepository.findAll();
		return Streamable.of(perfis).toList();
	}

}
