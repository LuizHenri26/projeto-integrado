package br.com.lhlibrarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.service.LivroService;

@Controller
@RequestMapping("/library")
public class IndexController {
	
	@Autowired
	private LivroService service;
	
	@GetMapping("/index")
	public String index() {
		return "/index";
	}
	
	@GetMapping("/entrar")
	public String entrar() {
		return "/entrar";
	}
	
	@GetMapping("/listar")
	public String listarLivros(Model model) {
		List<Livro> livros = service.getListarLivros();
		model.addAttribute("livros", livros);
		return "/index";
	}
	
	@PostMapping("/buscar")
	public String pesquisarPeloTituloAutor(Model model, @RequestParam("titulo") String titulo, @RequestParam("autor") String autor) {
		List<Livro> livros;
		if (titulo == null && autor == null) {
			return "redirect:/library/listar";
		} else {
			livros = service.filtrarLivros(titulo, autor);
		}
		model.addAttribute("livros", livros);
		return "/index";
	}

}
