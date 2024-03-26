package br.com.lhlibrarymanagement.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.lhlibrarymanagement.model.Perfil;
import br.com.lhlibrarymanagement.model.Usuario;

public class DetalheUsuario implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	public DetalheUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Perfil> perfis = usuario.getPerfis();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Perfil perfil : perfis) {
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(perfil.getNome());
			authorities.add(simpleGrantedAuthority);
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return usuario.getSenha();
	}

	@Override
	public String getUsername() {
		return usuario.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return usuario.isAtivo();
	}

	public String getNome() {
		return usuario.getNome();
	}

}
