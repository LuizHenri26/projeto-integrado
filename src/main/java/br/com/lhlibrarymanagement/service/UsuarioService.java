package br.com.lhlibrarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lhlibrarymanagement.model.Perfil;
import br.com.lhlibrarymanagement.model.Usuario;
import br.com.lhlibrarymanagement.model.repository.UsuarioRepository;
import br.com.lhlibrarymanagement.security.DetalheUsuario;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

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

	public Usuario buscaUsuarioPorLogin(String login) {
		Usuario usuario = usuarioRepository.findByLogin(login);
		return usuario;
	}

	public void alterarUsuario(Usuario usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		Integer id = usuarioRepository.MaxId();
		
		if (id == 1) {
			usuario.setId(Long.valueOf(id) + 1);
		}
		
		usuario.setSenha(crypt);
		usuarioRepository.save(usuario);
	}

	/**
	 * Cadastra os dados do usuario da aplicação.
	 * 
	 * @param usuario  - entidade usuário.
	 * @param idsPefis - identificador dos perfis habilitados.
	 */
	public void cadastrarUsuario(Usuario usuario) {
		List<Perfil> perfis = new ArrayList<Perfil>();
		long idPefil = 2;
		Perfil papel = perfilService.buscarPerfilPorId(idPefil);
		perfis.add(papel);
		usuario.setPerfis(perfis);
		alterarUsuario(usuario);
	}

	/**
	 * Edita os dados do usuário da aplicação, na tela de edição.
	 * 
	 * @param id       - identificador do usuário.
	 * @param idsPefis - ids de perfis caso mais de um tenha sigo habilitado.
	 * @param isAtivo  - se o usuário está ativo ou inativo.
	 */
	public void editarUsuario(Long id) {
		List<Perfil> perfis = new ArrayList<Perfil>();
		long idPefil = 2;
		Perfil papel = perfilService.buscarPerfilPorId(idPefil);
		perfis.add(papel);
		Usuario usuario = buscarUsuarioPorId(id);
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		usuario.setPerfis(perfis);
		usuarioRepository.save(usuario);
	}

	/**
	 * Verifica qual o tipo de perfil o possui na aplicação.
	 */
	public boolean isAutoriza(Usuario usuario, String nome) {
		for (Perfil perfil : usuario.getPerfis()) {
			if (perfil.getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}

	public List<Usuario> filtrarUsuarios(String nome, String login) {
		List<Usuario> usuarios = null;

		if (!nome.isBlank() && !login.isBlank()) {
			usuarios = usuarioRepository.findByNomeAndLoginContainingIgnoreCase(nome, login);
		}
		if (!nome.isBlank()) {
			usuarios = usuarioRepository.findByNomeContainingIgnoreCase(nome);
		}
		if (!login.isBlank()) {
			usuarios = usuarioRepository.findByLoginContainingIgnoreCase(login);
		}

		return usuarios;
	}
	
	/**
	 * Deleta o usuario
	 * 
	 * @param categoria - entidade usuario
	 */
	public void deletarUsuario(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username);

		if (usuario != null) {
			DetalheUsuario detalheUsuario = new DetalheUsuario(usuario);
			return detalheUsuario;

		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

}
