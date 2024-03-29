package br.com.lhlibrarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.service.LivroService;

@Controller
public class IndexController {
	
	@Autowired
	private LivroService service;
	
	@GetMapping("/")
	public String index(Model model) {
		List<Livro> livros = this.service.getListarLivros();
		model.addAttribute("livros", livros);
		return "index";
	}
	
	@GetMapping("/login")
	public String entrar() {
		return "login";
	}
}
