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

import br.com.lhlibrarymanagement.model.Emprestimo;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.model.Membro;
import br.com.lhlibrarymanagement.service.EmprestimoService;

@Controller
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;

	@GetMapping(value = "/emprestimo/salvar")
	public String formRealizarEmprestimo(Model model) {
		List<Livro> livros = this.emprestimoService.getLivrosDisponiveis();
		List<Membro> membros = this.emprestimoService.getMembros();
		model.addAttribute("emprestimo", new Emprestimo());
		model.addAttribute("membros", membros);
		model.addAttribute("livros", livros);
		return "registrar-emprestimo";
	}

	@PostMapping(value = "/emprestimo/cadastrar")
	public String realizarEmprestimo(Emprestimo emprestimo, BindingResult result, Model model,
			RedirectAttributes redirect) {
		// Validador option bean validation não funciona para essa situação
		if (emprestimo.getLivro() == null || emprestimo.getMembro() == null) {
			List<Livro> livros = this.emprestimoService.getLivrosDisponiveis();
			List<Membro> membros = this.emprestimoService.getMembros();
			model.addAttribute("emprestimo", new Emprestimo());
			model.addAttribute("membros", membros);
			model.addAttribute("livros", livros);
			model.addAttribute("campoLivroObrigatorio", "Campo obrigatório!");
			model.addAttribute("campoMembroObrigatorio", "Campo obrigatório!");
			return "registrar-emprestimo";
		}

		this.emprestimoService.registrarEmprestimo(emprestimo);
		redirect.addFlashAttribute("mensagem", "Empréstimo do livro efetuado com sucesso!");
		return "redirect:/emprestimo/salvar";
	}

	@GetMapping(value = "/emprestimo/listar")
	public String listarEmprestimosVigentes(Model model) {
		List<Emprestimo> emprestimos = this.emprestimoService.getListarEmprestimosVigentes();
		model.addAttribute("emprestimos", emprestimos);
		return "registrar-devolucao";
	}

	@PostMapping(value = "/emprestimo/buscar")
	public String pesquisarMembroPeloNome(Model model, @RequestParam("nome") String nome) {
		if (nome.isBlank()) {
			return "redirect:/emprestimo/listar";
		}
		List<Emprestimo> emprestimos = this.emprestimoService.filtrarMembros(nome);
		model.addAttribute("emprestimos", emprestimos);
		return "registrar-devolucao";
	}

	@GetMapping("/emprestimo/devolucao/{id}")
	public String registrarDevolucaoLivro(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Emprestimo emprestimo = this.emprestimoService.findById(id);
		model.addAttribute("emprestimo", emprestimo);
		emprestimoService.registrarDevolucao(emprestimo);
		redirect.addFlashAttribute("mensagemSucesso", "Devolução efetuada com sucesso!");
		return "redirect:/emprestimo/listar";
	}
}
