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
import br.com.lhlibrarymanagement.repository.UsuarioRepository;
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

	/**
	 * Cadastra os dados do usuario da aplicação.
	 * 
	 * @param usuario  - entidade usuário.
	 * @param idsPefis - identificador dos perfis habilitados.
	 */
	public void cadastrarUsuario(Usuario usuario, int[] idsPerfis) {
		List<Perfil> perfis = new ArrayList<Perfil>();
		for (int i = 0; i < idsPerfis.length; i++) {
			long idPerfil = idsPerfis[i];
			Perfil perfil = perfilService.buscarPerfilPorId(idPerfil);
			perfis.add(perfil);
		}
		usuario.setPerfis(perfis);
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		usuarioRepository.save(usuario);
	}

	/**
	 * Edita os dados do usuário da aplicação, na tela de edição.
	 * 
	 * @param id       - identificador do usuário.
	 * @param idsPefis - ids de perfis caso mais de um tenha sigo habilitado.
	 * @param isAtivo  - se o usuário está ativo ou inativo.
	 */
	public void editarUsuario(Usuario usuario, int[] idsPerfis, Long id) {
		List<Perfil> perfis = new ArrayList<Perfil>();
		for (int i = 0; i < idsPerfis.length; i++) {
			long idPerfil = idsPerfis[i];
			Perfil perfil = perfilService.buscarPerfilPorId(idPerfil);
			perfis.add(perfil);
		}
		Usuario usuarios = buscarUsuarioPorId(id);
		String senhaAtual = usuarios.getSenha();
		String senhaFutura = usuario.getSenha();

		if (!senhaAtual.equals(senhaFutura)) {
			String crypt = new BCryptPasswordEncoder().encode(senhaFutura);
			usuario.setSenha(crypt);
		}

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

	public boolean isLoginExistente(final Long id, final Usuario usuario) {
		Usuario usr = buscarUsuarioPorId(id);
		Usuario loginExistente = buscaUsuarioPorLogin(usuario.getLogin());
		String usuarioAtual = usr.getLogin();
		String usuarioFuturo = usuario.getLogin();
		return !usuarioAtual.equals(usuarioFuturo) && loginExistente != null;
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
