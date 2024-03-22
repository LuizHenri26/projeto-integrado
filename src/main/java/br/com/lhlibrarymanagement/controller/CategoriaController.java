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
import br.com.lhlibrarymanagement.service.CategoriaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@GetMapping("/salvar")
	public String adicionarNovaCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "/funcionario/cadastrarCategoria";
	}

	@PostMapping("/cadastrar")
	public String cadastrarCategoria(@Valid Categoria categoria, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "/funcionario/cadastrarCategoria";
		}
		Categoria c = service.buscarNomeDaCategoria(categoria.getNome());
		if (c != null) {
			model.addAttribute("categoriaExiste", "Categoria já existente, por favor coloque um nome diferente");
			return "/funcionario/cadastrarCategoria";
		}
		service.cadastrarCategoria(categoria);
		redirect.addFlashAttribute("mensagem", "Categoria adicionada com sucesso!");
		return "redirect:/categoria/salvar";
	}

	@GetMapping("/listar")
	public String listarTodasCategorias(Model model) {
		Iterable<Categoria> categorias = this.service.getCategorias();
		model.addAttribute("categorias", categorias);
		return "/funcionario/consultaCategorias";
	}

	@PostMapping("/buscar")
	public String pesquisarPeloNomeDaCategoria(Model model, @RequestParam("nome") String nome) {
		if (nome == null) {
			return "redirect:/categoria/listar";
		}
		List<Categoria> categorias = service.filtrarPeloNome(nome);
		model.addAttribute("categorias", categorias);
		return "/funcionario/consultaCategorias";
	}

	@GetMapping("/deletar/{id}")
	public String deletarCategoria(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Categoria categoria = service.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
		if (!categoria.getLivro().isEmpty()) {
			redirect.addFlashAttribute("mensagem", "Categoria vinculada a livro!");
			return "redirect:/categoria/listar";
		}
		this.service.deletarCategoria(categoria);
		model.addAttribute("categoria", categoria);
		return "redirect:/categoria/listar";
	}

	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable("id") Long id, Model model) {
		Optional<Categoria> categoriaAnterior = this.service.findById(id);
		if (!categoriaAnterior.isPresent()) {
			throw new IllegalArgumentException("Categoria Inválida: " + id);
		}
		Categoria categoria = categoriaAnterior.get();
		model.addAttribute("categoria", categoria);
		return "/funcionario/editarCategoria";
	}

	@PostMapping("/editar/{id}")
	public String editarCategoria(@PathVariable("id") Long id, @Valid Categoria categoria, Model model,
			BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			categoria.setId(id);
			return "/funcionario/editarCategoria";
		}
		Categoria c = service.buscarNomeDaCategoria(categoria.getNome());
		if (c != null) {
			model.addAttribute("categoriaExistente", "Categoria já existente, por favor coloque um nome diferente.");
			return "/funcionario/editarCategoria";
		}
		service.cadastrarCategoria(categoria);
		return "redirect:/categoria/listar";
	}

}
