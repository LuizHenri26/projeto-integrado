package br.com.lhlibrarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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


	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	public Usuario buscaUsuarioPorLogin(String login) {
		return usuarioRepository.findByLogin(login);
	}
	
	public Usuario buscarUsuarioPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário com id : " + id + " não existe"));
	}

	public int countLoginExistente(String nome) {
		return usuarioRepository.countByLogin(nome);
	}

	/**
	 * Cadastra os dados do usuario da aplicação.
	 * 
	 * @param usuario - entidade usuário.
	 */
	public void cadastrarUsuario(Usuario usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		usuarioRepository.save(usuario);
	}
	
	/**
	 * Edita os dados do usuário da aplicação, na tela de edição.
	 * 
	 * @param id - identificador do usuário.
	 */
	public void editarUsuario(Usuario usuario, Long id) {
		Usuario usuarioId = buscarUsuarioPorId(id);
		String senhaAtual = usuarioId.getSenha();
		String senhaFutura = usuario.getSenha();
		if (!senhaAtual.equals(senhaFutura)) {
			String crypt = new BCryptPasswordEncoder().encode(senhaFutura);
			usuario.setSenha(crypt);
		}
		usuarioRepository.save(usuario);
	}
	
	public void apagarUsuarioPorId(Long id) {
		Usuario usuario = buscarUsuarioPorId(id);
		usuarioRepository.delete(usuario);
	}

	/**
	 * Verifica qual o tipo de perfil o possui na aplicação.
	 */
	public boolean isAutoriza(Usuario usuario, String nome) {
		Perfil perfil = usuario.getPerfil();
		if (perfil.getNome().equals(nome)) {
			return true;
		}
		return false;
	}

	public List<Usuario> filtrarUsuarioPeloNome(String nome) {
		return usuarioRepository.findByNomeContainingIgnoreCase(nome);
	}

	/**
	 * Deleta o usuario
	 * 
	 * @param usuario - entidade usuario
	 */
	public void deletarUsuario(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}

	public boolean isLoginExistente(final Long id, final Usuario usuario) {
		Usuario usr = buscarUsuarioPorId(id);
		int countLoginExistente = countLoginExistente(usuario.getLogin());
		String usuarioAtual = usr.getLogin();
		String usuarioFuturo = usuario.getLogin();
		return !usuarioAtual.equals(usuarioFuturo) && countLoginExistente > 0;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = buscaUsuarioPorLogin(username);
		if (usuario != null) {
			DetalheUsuario detalheUsuario = new DetalheUsuario(usuario);
			return detalheUsuario;

		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

}
