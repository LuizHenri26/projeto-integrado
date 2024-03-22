package br.com.lhlibrarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Perfil;
import br.com.lhlibrarymanagement.model.Usuario;
import br.com.lhlibrarymanagement.model.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilService perfilService;

	public Usuario buscarUsuarioPorId(Long id) {
		Optional<Usuario> opt = usuarioRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IllegalArgumentException("Usuário com id : " + id + " não existe");
		}
	}

	public void apagarUsuarioPorId(Long id) {
		Usuario usuario = buscarUsuarioPorId(id);
		usuarioRepository.delete(usuario);
	}

	public List<Usuario> listarUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios;
	}

	public Usuario buscaUsuarioPorEmail(String login) {
		Usuario usuario = usuarioRepository.findByEmail(login);
		return usuario;
	}

	public void alterarUsuario(Usuario usuario) {
		usuario.setSenha(usuario.getSenha());
		usuarioRepository.save(usuario);
	}
	
	public void atribuirPerfil(Usuario usuario, int[] idsPefis) {
		List<Perfil> perfis = new ArrayList<Perfil>();
		for (int i = 0; i < idsPefis.length; i++) {
			long idPefil = idsPefis[i];
			Perfil papel = perfilService.buscarPerfilPorId(idPefil);
			perfis.add(papel);
		}
		usuario.setPerfis(perfis);
		usuario.setAtivo(true);
		alterarUsuario(usuario);
	}

	public void atribuirPerfilParaUsuario(Long id, int[] idsPefis, boolean isAtivo) {
		List<Perfil> perfis = new ArrayList<Perfil>();
		for (int i = 0; i < idsPefis.length; i++) {
			long idPefil = idsPefis[i];
			Perfil papel = perfilService.buscarPerfilPorId(idPefil);
			perfis.add(papel);
		}
		Usuario usuario = buscarUsuarioPorId(id);
		usuario.setPerfis(perfis);
		usuario.setAtivo(isAtivo);
		alterarUsuario(usuario);
	}

	/**
	 * Verifica qual o tipo de perfil o usuario tem na aplicação.
	 */
	public boolean isAutoriza(Usuario usuario, String nome) {
		for (Perfil perfil : usuario.getPerfis()) {
			if (perfil.getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}

}
