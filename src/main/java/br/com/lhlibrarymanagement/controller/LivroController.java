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

import br.com.lhlibrarymanagement.model.Categoria;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.model.Setor;
import br.com.lhlibrarymanagement.service.LivroService;
import jakarta.validation.Valid;

@Controller
public class LivroController {

	@Autowired
	private LivroService service;

	@GetMapping("/livro/salvar")
	public String formCadastrarLivro(Model model) {
		List<Categoria> filtroCategorias = service.getCategorias();
		List<Setor> filtroSetores = service.getSetores();
		model.addAttribute("livro", new Livro());
		model.addAttribute("categoriasList", filtroCategorias);
		model.addAttribute("setoresList", filtroSetores);
		return "cadastrar-livro";
	}

	@PostMapping("/livro/cadastrar")
	public String adicionarNovoLivro(@Valid Livro livro, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			List<Categoria> filtroCategorias = service.getCategorias();
			List<Setor> filtroSetores = service.getSetores();
			model.addAttribute("categoriasList", filtroCategorias);
			model.addAttribute("setoresList", filtroSetores);
			model.addAttribute("mensagemCategoriaVazia", "ISBN já cadastrado, por favor coloque um valor diferente");
			return "cadastrar-livro";
		}

		Livro l = service.findByIsbn(livro.getIsbn());
		if (l != null) {
			model.addAttribute("isbnExistente", "ISBN já cadastrado, por favor coloque um valor diferente");
			return "cadastrar-livro";
		}

		livro.setStatus("Disponivel");
		service.cadastraLivro(livro);
		redirect.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
		return "redirect:/livro/salvar";
	}

	@GetMapping("/livro/listar")
	public String listarLivros(Model model) {
		List<Livro> livros = this.service.getListarLivros();
		model.addAttribute("livros", livros);
		return "consulta-livros";
	}

	@PostMapping("/livro/buscar")
	public String pesquisarPeloTitulo(Model model, @RequestParam("titulo") String titulo) {
		List<Livro> livros;
		if (titulo.isBlank()) {
			return "redirect:/livro/listar";
		} else {
			livros = service.filtrarLivroPeloTitulo(titulo);
		}
		model.addAttribute("livros", livros);
		return "consulta-livros";
	}

	@GetMapping("/livro/deletar/{id}")
	public String deletarLivro(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Livro livro = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
		if (!livro.getEmprestimos().isEmpty()) {
			redirect.addFlashAttribute("mensagem", "Livro Emprestado!");
			return "redirect:/livro/listar";
		}
		this.service.deletarLivro(livro);
		model.addAttribute("livro", livro);
		return "redirect:/livro/listar";
	}

}
