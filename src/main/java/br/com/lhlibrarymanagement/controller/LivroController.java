package br.com.lhlibrarymanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lhlibrarymanagement.model.Categoria;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.model.Setor;
import br.com.lhlibrarymanagement.service.LivroService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/livro")
public class LivroController {
	
	@Autowired
	private LivroService service;
	
	@GetMapping("/salvar")
	public String formCadastrarLivro(Model model) {
		List<Categoria> filtroCategorias = service.getCategorias();
		List<Setor> filtroSetores = service.getSetores();
		model.addAttribute("livro", new Livro());
		model.addAttribute("categoriasList", filtroCategorias);
		model.addAttribute("setoresList", filtroSetores);
		return "/formCadastrarLivro";
	}
	
	@PostMapping("/cadastrarLivro")
	public String adicionarNovoLivro(@Valid Livro livro, BindingResult result, Model model, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			List<Categoria> filtroCategorias = service.getCategorias();
			List<Setor> filtroSetores = service.getSetores();
			model.addAttribute("categoriasList", filtroCategorias);
			model.addAttribute("setoresList", filtroSetores);
			return "/formCadastrarLivro";
		}
		livro.setStatus("Disponivel");
		service.cadastraLivro(livro);
		redirect.addFlashAttribute("mensagem", "Livro adicionado com sucesso!");
		return "redirect:/livro/salvar";
	}
	
	@GetMapping("/listar")
	public String listarLivros(Model model) {
		Iterable<Livro> livros =  this.service.getLivros();
		model.addAttribute("livros", livros);
		return "/consultaLivros";
	}
	
	@PostMapping("/buscar")
	public String pesquisarPeloTituloAutor(Model model, @RequestParam("titulo") String titulo, @RequestParam("autor") String autor) {
		List<Livro> livros;
		if (titulo == null && autor == null) {
			return "redirect:/livro/listar";
		} else {
			livros = service.filtrarLivros(titulo, autor);
		}
		model.addAttribute("livros", livros);
		return "/consultaLivros";
	}
	
	@GetMapping("/editar/{id}")
	public String editarLivro(@PathVariable("id") Long id, Model model) {
		Optional<Livro> livroAnterior =  this.service.findById(id);
		Livro livro = livroAnterior.get();
		List<Categoria> filtroCategorias = service.getCategorias();
		List<Setor> filtroSetores = service.getSetores();
		model.addAttribute("categoriasList", filtroCategorias);
		model.addAttribute("setoresList", filtroSetores);
		model.addAttribute("livro", livro);
		return "/formEditarLivro";
	}
	
	@PostMapping("/editar/{id}")
	public String editarLivro(@PathVariable("id") Long id, @Valid Livro livro, Model model, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			livro.setId(id);
			return "/formEditarLivro";
		}
		Livro l = service.findByIsbn(livro.getIsbn());
		if (l != null) {
			model.addAttribute("isbnExistente", "ISBN já cadastrado, por favor coloque um valor diferente");
			return "/formEditarLivro";
		}
		service.cadastraLivro(livro);
		redirect.addFlashAttribute("mensagem", "Categoria alterada com sucesso!");
		return "/formEditarLivro";
	}
	
	@GetMapping("/deletar/{id}")
	public String deletarLivro(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Livro livro =  service.findById(id).orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
		if (!livro.getEmprestimos().isEmpty()) {
			redirect.addFlashAttribute("mensagem", "Livro Emprestado!");
			return "redirect:/livro/listar";
		}
		this.service.deletarLivro(livro);
		model.addAttribute("livro", livro);
		return "redirect:/livro/listar";
	}

}
