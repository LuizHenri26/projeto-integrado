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
import br.com.lhlibrarymanagement.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
public class UsuarioController {

	@Autowired
	public UsuarioService usuarioService;

	@GetMapping("/usuario/salvar")
	public String adicionarUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "admin/cadastrar-usuario";
	}

	@PostMapping("/usuario/cadastrar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "admin/cadastrar-usuario";
		}

		Usuario usr = usuarioService.buscaUsuarioPorLogin(usuario.getLogin());

		if (usr != null) {
			model.addAttribute("loginExistente", "Login já cadastrado, por favor insira um login diferente!");
			return "admin/cadastrar-usuario";
		}

		usuarioService.cadastrarUsuario(usuario);
		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return "redirect:/usuario/salvar";
	}

	@GetMapping("/usuario/listar")
	public String consultaUsuarios(Model model) {
		Iterable<Usuario> usuarios = usuarioService.listarUsuarios();
		model.addAttribute("usuarios", usuarios);
		return "admin/consultar-usuarios";
	}

	@PostMapping("/usuario/buscar")
	public String pesquisarPeloFiltroNomeLoginUsuario(Model model, @RequestParam("nome") String nome,
			@RequestParam("login") String login) {
		List<Usuario> usuarios;
		if (nome.isBlank() && login.isBlank()) {
			return "redirect:/usuario/listar";
		} else {
			usuarios = usuarioService.filtrarUsuarios(nome, login);
		}
		model.addAttribute("usuarios", usuarios);
		return "admin/consultar-usuarios";
	}

	@GetMapping("/usuario/deletar/{id}")
	public String deletarUsuario(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
		usuarioService.deletarUsuario(usuario);
		model.addAttribute("usuario", usuario);
		return "redirect:/usuario/listar";
	}

}
