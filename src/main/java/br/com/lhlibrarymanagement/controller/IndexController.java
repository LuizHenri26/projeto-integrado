package br.com.lhlibrarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.service.LivroService;

@Controller
public class IndexController {
	
	@Autowired
	private LivroService service;
	
	@GetMapping("/index")
	public String index() {
		return "/index";
	}
	
	@GetMapping("/login")
	public String entrar() {
		return "/login";
	}
	
	@GetMapping("/listar")
	public String listarLivros(Model model) {
		List<Livro> livros = this.service.getListarLivros();
		model.addAttribute("livros", livros);
		return "/consulta-livros";
	}
	
	@PostMapping("/buscar")
	public String pesquisarPeloTituloAutor(Model model, @RequestParam("titulo") String titulo, @RequestParam("autor") String autor) {
		List<Livro> livros;
		if (titulo.isBlank() && autor.isBlank()) {
			return "redirect:/listar";
		} else {
			livros = service.filtrarLivros(titulo, autor);
		}
		model.addAttribute("livros", livros);
		return "redirect:/listar";
	}

}
