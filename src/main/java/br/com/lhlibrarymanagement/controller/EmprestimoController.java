package br.com.lhlibrarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lhlibrarymanagement.model.Emprestimo;
import br.com.lhlibrarymanagement.model.Livro;
import br.com.lhlibrarymanagement.model.Membro;
import br.com.lhlibrarymanagement.service.EmprestimoService;
import jakarta.validation.Valid;

@Controller
public class EmprestimoController {

	@Autowired
	private EmprestimoService service;

	@GetMapping("/emprestimo/salvar")
	public String formCadastrarLivro(Model model) {
		List<Livro> livros = service.getLivrosDisponiveis();
		List<Membro> membros = service.getMembros();
		model.addAttribute("emprestimo", service.getNewEmprestimo(new Emprestimo()));
		model.addAttribute("membros", membros);
		model.addAttribute("livros", livros);
		return "registrar-emprestimo";
	}

	@PostMapping("/emprestimo/cadastrar")
	public String adicionarNovoLivro(Emprestimo emprestimo, BindingResult result, Model model,
			RedirectAttributes redirect) {
		
		if (emprestimo.getLivro() == null) {
			List<Livro> livros = service.getLivrosDisponiveis();
			List<Membro> membros = service.getMembros();
			model.addAttribute("membros", membros);
			model.addAttribute("livros", livros);
			model.addAttribute("emprestimo", service.getNewEmprestimo(new Emprestimo()));
			model.addAttribute("tituloLivroObrigatorio", "O campo titulo do livro é obrigatório!");
			return "registrar-emprestimo";
		}
		
		if (emprestimo.getMembro() == null) {
			List<Livro> livros = service.getLivrosDisponiveis();
			List<Membro> membros = service.getMembros();
			model.addAttribute("membros", membros);
			model.addAttribute("livros", livros);
			model.addAttribute("emprestimo", service.getNewEmprestimo(new Emprestimo()));
			model.addAttribute("campoMembroObrigatorio", "O campo membro é obrigatório!");
			return "registrar-emprestimo";
		}
		
		service.registrarEmprestimo(emprestimo);
		redirect.addFlashAttribute("mensagem", "Emprestimo efetuado com sucesso!");
		return "redirect:/emprestimo/salvar";
	}
}
