package br.com.lhlibrarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lhlibrarymanagement.model.Usuario;
import br.com.lhlibrarymanagement.service.PerfilService;
import br.com.lhlibrarymanagement.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
public class UsuarioController {

	@Autowired
	public UsuarioService usuarioService;
	@Autowired
	public PerfilService perfilService;

	@GetMapping(value = "/usuario/salvar")
	public String adicionarUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("perfis", perfilService.listarPerfil());
		return "admin/cadastrar-usuario";
	}

	@PostMapping(value = "/usuario/cadastrar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			model.addAttribute("perfis", this.perfilService.listarPerfil());
			return "admin/cadastrar-usuario";
		}

		Integer countLoginExistente = usuarioService.countLoginExistente(usuario.getLogin());

		if (countLoginExistente > 0) {
			model.addAttribute("listaPerfis", this.perfilService.listarPerfil());
			model.addAttribute("loginExistente", "Login já cadastrado!");
			return "admin/cadastrar-usuario";
		}

		usuarioService.cadastrarUsuario(usuario);
		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return "redirect:/usuario/salvar";
	}

	@GetMapping(value = "/usuario/listar")
	public String consultaUsuarios(Model model) {
		Iterable<Usuario> usuarios = this.usuarioService.listarUsuarios();
		model.addAttribute("usuarios", usuarios);
		return "admin/consultar-usuarios";
	}

	@PostMapping(value = "/usuario/buscar")
	public String pesquisarPeloFiltroNomeLoginUsuario(Model model, @RequestParam("nome") String nome) {
		if (nome.isBlank()) {
			return "redirect:/usuario/listar";
		}
		List<Usuario> usuarios = usuarioService.filtrarUsuarioPeloNome(nome);
		model.addAttribute("usuarios", usuarios);
		return "admin/consultar-usuarios";
	}

	@GetMapping(value = "/usuario/deletar/{id}")
	public String deletarUsuario(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Usuario usuario = this.usuarioService.buscarUsuarioPorId(id);
		this.usuarioService.deletarUsuario(usuario);
		model.addAttribute("usuario", usuario);
		redirect.addFlashAttribute("mensagem", "Usuario deletado com sucesso!");
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/usuario/editar/{id}")
	public String editarUsuario(@PathVariable("id") Long id, Model model) {
		Usuario usuario = this.usuarioService.buscarUsuarioPorId(id);
		model.addAttribute("perfis", perfilService.listarPerfil());
		model.addAttribute("usuario", usuario);
		return "admin/editar-usuario";
	}

	@PostMapping(value = "/usuario/editar/{id}")
	public String editarUsuario(@PathVariable("id") Long id, @Valid Usuario usuario, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			usuario.setId(id);
			model.addAttribute("perfis", this.perfilService.listarPerfil());
			return "admin/editar-usuario";
		}

		if (usuarioService.isLoginExistente(id, usuario)) {
			model.addAttribute("listaPerfis", this.perfilService.listarPerfil());
			model.addAttribute("loginExistente", "Login já cadastrado!");
			return "admin/editar-usuario";
		}

		usuarioService.editarUsuario(usuario, id);
		redirect.addFlashAttribute("mensagem", "Alteração realizada com sucesso!");
		return "redirect:/usuario/listar";
	}

}
