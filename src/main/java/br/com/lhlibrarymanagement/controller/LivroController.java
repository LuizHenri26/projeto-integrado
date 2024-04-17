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

import br.com.lhlibrarymanagement.enums.StatusENUM;
import br.com.lhlibrarymanagement.model.Categoria;
import br.com.lhlibrarymanagement.model.Idioma;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.model.Setor;
import br.com.lhlibrarymanagement.service.LivroService;
import jakarta.validation.Valid;

@Controller
public class LivroController {

	@Autowired
	private LivroService livroService;

	@GetMapping(value = "/livro/salvar")
	public String formCadastrarLivro(Model model) {
		List<Categoria> filtroCategorias = livroService.getCategorias();
		List<Setor> filtroSetores = livroService.getSetores();
		List<Idioma> filtroIdiomas = livroService.getIdiomas();
		model.addAttribute("livro", new Livro());
		model.addAttribute("categoriasList", filtroCategorias);
		model.addAttribute("setoresList", filtroSetores);
		model.addAttribute("idiomasList", filtroIdiomas);
		return "cadastrar-livro";
	}

	@PostMapping(value = "/livro/cadastrar")
	public String adicionarNovoLivro(@Valid Livro livro, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			List<Categoria> filtroCategorias = livroService.getCategorias();
			List<Setor> filtroSetores = livroService.getSetores();
			List<Idioma> filtroIdiomas = livroService.getIdiomas();
			model.addAttribute("categoriasList", filtroCategorias);
			model.addAttribute("setoresList", filtroSetores);
			model.addAttribute("idiomasList", filtroIdiomas);
			return "cadastrar-livro";
		}

		Integer countIsbnExistente = livroService.countIsbnExistente(livro.getIsbn());

		if (countIsbnExistente > 0) {
			List<Categoria> filtroCategorias = livroService.getCategorias();
			List<Setor> filtroSetores = livroService.getSetores();
			List<Idioma> filtroIdiomas = livroService.getIdiomas();
			model.addAttribute("categoriasList", filtroCategorias);
			model.addAttribute("setoresList", filtroSetores);
			model.addAttribute("idiomasList", filtroIdiomas);
			model.addAttribute("isbnExistente", "ISBN já cadastrado!");
			return "cadastrar-livro";
		}
		livro.setStatus(StatusENUM.DISPONIVEL);
		livroService.cadastraLivro(livro);
		redirect.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
		return "redirect:/livro/salvar";
	}

	@GetMapping(value = "/livro/listar")
	public String listarLivros(Model model) {
		List<Livro> livros = this.livroService.getListarLivros();
		model.addAttribute("livros", livros);
		return "consulta-livros";
	}

	@PostMapping("/livro/buscar")
	public String pesquisarPeloTitulo(Model model, @RequestParam("titulo") String titulo) {
		List<Livro> livros;
		if (titulo.isBlank()) {
			return "redirect:/livro/listar";
		}
		livros = livroService.filtrarLivroPeloTitulo(titulo);
		model.addAttribute("livros", livros);
		return "consulta-livros";
	}

	@GetMapping(value = "/livro/deletar/{id}")
	public String deletarLivro(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Livro livro = livroService.findById(id);
		if (livro.getStatus().equals(StatusENUM.EMPRESTADO)) {
			redirect.addFlashAttribute("mensagemErro", "Livro emprestado!");
			return "redirect:/livro/listar";
		}
		livroService.deletarLivro(livro);
		model.addAttribute("livro", livro);
		redirect.addFlashAttribute("mensagemSucesso", "Livro deletado com sucesso!");
		return "redirect:/livro/listar";
	}

	@GetMapping(value = "/livro/editar/{id}")
	public String editarLivro(@PathVariable("id") Long id, Model model) {
		Livro livro = livroService.findById(id);
		List<Categoria> filtroCategorias = livroService.getCategorias();
		List<Setor> filtroSetores = livroService.getSetores();
		List<Idioma> filtroIdiomas = livroService.getIdiomas();
		model.addAttribute("categoriasList", filtroCategorias);
		model.addAttribute("setoresList", filtroSetores);
		model.addAttribute("idiomasList", filtroIdiomas);
		model.addAttribute("livro", livro);
		return "editar-livro";
	}

	@PostMapping(value = "/livro/editar/{id}")
	public String editarLivro(@PathVariable("id") Long id, @Valid Livro livro, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			List<Categoria> filtroCategorias = livroService.getCategorias();
			List<Setor> filtroSetores = livroService.getSetores();
			List<Idioma> filtroIdiomas = livroService.getIdiomas();
			model.addAttribute("categoriasList", filtroCategorias);
			model.addAttribute("setoresList", filtroSetores);
			model.addAttribute("idiomasList", filtroIdiomas);
			return "editar-livro";
		}

		if (livroService.isIsbnExistente(id, livro)) {
			List<Categoria> filtroCategorias = livroService.getCategorias();
			List<Setor> filtroSetores = livroService.getSetores();
			List<Idioma> filtroIdiomas = livroService.getIdiomas();
			model.addAttribute("categoriasList", filtroCategorias);
			model.addAttribute("setoresList", filtroSetores);
			model.addAttribute("idiomasList", filtroIdiomas);
			model.addAttribute("isbnExistente", "ISBN já cadastrado!");
			return "editar-livro";
		}
		livroService.editarLivro(livro, id);
		redirect.addFlashAttribute("mensagemSucesso", "Alteração realizada com sucesso!");
		return "redirect:/livro/listar";
	}

}
