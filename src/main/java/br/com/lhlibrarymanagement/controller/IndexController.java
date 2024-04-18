package br.com.lhlibrarymanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.lhlibrarymanagement.service.LivroService;

@Controller
public class IndexController {

	@Autowired
	private LivroService service;

	@GetMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("livros", this.service.getListarLivros());
		return "index";
	}

	@GetMapping(value = "/login")
	public String entrar() {
		return "login";
	}

	@PostMapping(value = "/buscar")
	public String pesquisarPeloTitulo(Model model, @RequestParam("titulo") String titulo) {
		if (titulo.isBlank()) {
			return "redirect:/";
		}
		model.addAttribute("livros", this.service.filtrarLivroPeloTitulo(titulo));
		return "index";
	}
}
