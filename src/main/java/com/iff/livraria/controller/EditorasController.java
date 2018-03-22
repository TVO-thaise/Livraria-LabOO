package com.iff.livraria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iff.livraria.model.Editora;
import com.iff.livraria.repository.Editoras;

@Controller
@RequestMapping("/editoras")
public class EditorasController {

	@Autowired
	private Editoras editoras;

	@GetMapping()
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("ListaEditoras");
		List <Editora> lista = editoras.findAll();
		mv.addObject("editoras",lista);
		mv.addObject(new Editora());
		return mv;
	}
	
	@PostMapping()
	public String salvar(Editora e) {
		editoras.save(e);
		return "redirect:/editoras";

	}



	//URL de chamada http://.../excluir/1234				
	@RequestMapping(value = "/excluir/{id}")
	public String excluirEditoraByPathVariable(@PathVariable Long id, RedirectAttributes attributes)	{
		this.editoras.delete(id);
		attributes.addFlashAttribute("mensagem", "Editora excluída com sucesso!");
		return "redirect:/editoras";
	}

	//URL de chamada http://.../alterar/1234
	@RequestMapping(value = "/alterar/{id}")
	public ModelAndView alterar(@PathVariable Long id, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("ListaEditoras");
		List <Editora> lista = editoras.findAll();
		mv.addObject("editoras",lista);
		Editora editora = editoras.findOne(id);
		mv.addObject(editora);
		return mv;
	}

}