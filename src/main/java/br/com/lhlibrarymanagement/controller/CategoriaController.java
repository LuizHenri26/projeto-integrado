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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lhlibrarymanagement.model.Categoria;
import br.com.lhlibrarymanagement.service.CategoriaService;
import jakarta.validation.Valid;

@Controller
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/categoria/salvar")
	public String adicionarNovaCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "cadastrar-categoria";
	}

	@PostMapping("/categoria/cadastrar")
	public String cadastrarCategoria(@Valid Categoria categoria, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "cadastrar-categoria";
		}
		Categoria c = categoriaService.buscarNomeDaCategoria(categoria.getNome());
		if (c != null) {
			model.addAttribute("categoriaExiste", "Categoria já existente, por favor coloque um nome diferente");
			return "cadastrar-categoria";
		}
		categoriaService.cadastrarCategoria(categoria);
		redirect.addFlashAttribute("mensagem", "Categoria cadastrada com sucesso!");
		return "redirect:/categoria/salvar";
	}

	@GetMapping("/categoria/listar")
	public String listarTodasCategorias(Model model) {
		Iterable<Categoria> categorias = this.categoriaService.getCategorias();
		model.addAttribute("categorias", categorias);
		return "consulta-categorias";
	}

	@PostMapping("/categoria/buscar")
	public String pesquisarPeloNomeDaCategoria(Model model, @RequestParam("nome") String nome) {
		if (nome.isBlank()) {
			return "redirect:/categoria/listar";
		}
		List<Categoria> categorias = categoriaService.filtrarPeloNome(nome);
		model.addAttribute("categorias", categorias);
		return "consulta-categorias";
	}

	@GetMapping("/categoria/deletar/{id}")
	public String deletarCategoria(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Categoria categoria = categoriaService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
		if (!categoria.getLivro().isEmpty()) {
			redirect.addFlashAttribute("mensagem", "Categoria vinculada a livro!");
			return "redirect:/categoria/listar";
		}
		this.categoriaService.deletarCategoria(categoria);
		model.addAttribute("categoria", categoria);
		return "redirect:/categoria/listar";
	}

	@GetMapping("/categoria/editar/{id}")
	public String editarCategoria(@PathVariable("id") Long id, Model model) {
		Optional<Categoria> categoriaAnterior = this.categoriaService.findById(id);
		if (!categoriaAnterior.isPresent()) {
			throw new IllegalArgumentException("Categoria Inválida: " + id);
		}
		Categoria categoria = categoriaAnterior.get();
		model.addAttribute("categoria", categoria);
		return "editar-categoria";
	}

	@PostMapping("/categoria/editar/{id}")
	public String editarCategoria(@PathVariable("id") Long id, @Valid Categoria categoria, Model model,
			BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			categoria.setId(id);
			return "editar-categoria";
		}
		Categoria c = categoriaService.buscarNomeDaCategoria(categoria.getNome());
		if (c != null) {
			model.addAttribute("categoriaExistente", "Categoria já existente, por favor coloque um nome diferente.");
			return "editar-categoria";
		}
		categoriaService.cadastrarCategoria(categoria);
		return "redirect:/categoria/listar";
	}

}
