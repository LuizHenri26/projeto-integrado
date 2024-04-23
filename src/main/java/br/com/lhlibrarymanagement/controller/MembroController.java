package br.com.lhlibrarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lhlibrarymanagement.model.Membro;
import br.com.lhlibrarymanagement.service.MembroService;
import jakarta.validation.Valid;

@Controller
public class MembroController {

	@Autowired
	private MembroService membroService;

	@GetMapping("/membro/salvar")
	public String formCadastrarLivro(Model model) {
		model.addAttribute("membro", new Membro());
		return "cadastrar-membro";
	}

	@PostMapping("/membro/cadastrar")
	public String adicionarNovoMembro(@Valid Membro membro, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "cadastrar-membro";
		}

		int countCpfExistente = membroService.countCpfExistente(membro.getCpf());

		if (countCpfExistente > 0) {
			model.addAttribute("cpfExistente", "CPF já cadastrado!");
			return "cadastrar-membro";
		}

		this.membroService.cadastrarMembro(membro);
		redirect.addFlashAttribute("mensagem", "Membro cadastrado com sucesso!");
		return "redirect:/membro/salvar";
	}

	@GetMapping("/membro/listar")
	public String listarTodosMembros(Model model) {
		model.addAttribute("membros", this.membroService.getMembros());
		return "consulta-membros";
	}

	@PostMapping("/membro/buscar")
	public String pesquisarMembroPeloNome(Model model, @RequestParam("nome") String nome) {
		if (nome.isBlank()) {
			return "redirect:/membro/listar";
		}
		model.addAttribute("membros", this.membroService.filtrarMembroPeloNome(nome));
		return "consulta-membros";
	}

	@GetMapping("/membro/deletar/{id}")
	public String deletarMembro(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Membro membro = membroService.findById(id);
		int quantidadeEmprestimoVigente = membroService.countEmprestimoVigenteMembro(id);
		if (quantidadeEmprestimoVigente > 0) {
			redirect.addFlashAttribute("mensagemErro", "Membro possui empréstimo vigente!");
			return "redirect:/membro/listar";
		}
		this.membroService.deletarMembro(membro);
		model.addAttribute("membro", membro);
		redirect.addFlashAttribute("mensagemSucesso", "Membro deletado com sucesso!!");
		return "redirect:/membro/listar";
	}

	@GetMapping("/membro/editar/{id}")
	public String editarMembro(@PathVariable("id") Long id, Model model) {
		Membro membro = this.membroService.findById(id);
		model.addAttribute("membro", membro);
		return "editar-membro";
	}

	@PostMapping("/membro/editar/{id}")
	public String editarMembro(@PathVariable("id") Long id, @Valid Membro membro, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "editar-membro";
		}

		if (membroService.isCpfExistente(id, membro)) {
			model.addAttribute("cpfExistente", "CPF já cadastrado!");
			return "editar-membro";
		}
		membroService.cadastrarMembro(membro);
		redirect.addFlashAttribute("mensagemSucesso", "Alteração realizada com sucesso!");
		return "redirect:/membro/listar";
	}
}
