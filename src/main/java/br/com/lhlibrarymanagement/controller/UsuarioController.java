package br.com.lhlibrarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lhlibrarymanagement.model.Perfil;
import br.com.lhlibrarymanagement.model.Usuario;
import br.com.lhlibrarymanagement.service.PerfilService;
import br.com.lhlibrarymanagement.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	public UsuarioService usuarioService;
	@Autowired
	public PerfilService perfilService;

	@GetMapping("/inicio")
	public String index(@CurrentSecurityContext(expression = "authentication.name") String email) {
		Usuario usuario = usuarioService.buscaUsuarioPorEmail(email);
		String redirectURL = "";
		if (usuarioService.isAutoriza(usuario, "ADMIN")) {
			redirectURL = "/admin/cadastrarUsuario";
		} else if (usuarioService.isAutoriza(usuario, "FUCIONARIO")) {
			redirectURL = "/funcionario/cadastrarCategoria";
		}
		return redirectURL;
	}

	@GetMapping("/novo")
	public String adicionarUsuario(Model model) {
		List<Perfil> perfis = perfilService.listarPapel();
		model.addAttribute("listaPerfis", perfis);
		model.addAttribute("usuario", new Usuario());
		return "/admin/cadastrarUsuario";
	}

	@PostMapping("/salvar")
	public String salvarUsuario(@Valid Usuario usuario,// @RequestParam(value = "pps", required = false) int[] perfis,
			BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "/admin/cadastrarUsuario";
		}

		Usuario usr = usuarioService.buscaUsuarioPorEmail(usuario.getEmail());

		if (usr != null) {
			model.addAttribute("emailExistente", "Email já cadastrado, por favor insira um E-mail diferente!");
			return "/admin/cadastrarUsuario";
		}

		/*
		 * if (perfis == null) { attributes.addFlashAttribute("mensage",
		 * "Pelo menos um perfil deve ser escolhido!"); return
		 * "/admin/cadastrarUsuario"; }
		 */
		
		//usuarioService.atribuirPerfil(usuario, perfis);
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return "redirect:/usuario/novo";
	}

}
