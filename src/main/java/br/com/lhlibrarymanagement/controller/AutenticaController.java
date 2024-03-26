package br.com.lhlibrarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.lhlibrarymanagement.model.Usuario;
import br.com.lhlibrarymanagement.service.UsuarioService;

@Controller
@RequestMapping("/autentica")
public class AutenticaController {
	
	@Autowired
	public UsuarioService usuarioService;
	
	@GetMapping("/inicio")
	public String index(@CurrentSecurityContext(expression = "authentication.name") String login) {
		Usuario usuario = usuarioService.buscaUsuarioPorLogin(login);
		String redirectURL = "";
		if (usuarioService.isAutoriza(usuario, "ADMIN")) {
			redirectURL = "redirect:/usuario/listar";
		} else if (usuarioService.isAutoriza(usuario, "FUNCIONARIO")) {
			redirectURL = "redirect:/categoria/listar";
		}
		return redirectURL;
	}

}
