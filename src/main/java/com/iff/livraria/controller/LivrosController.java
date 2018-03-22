package com.iff.livraria.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iff.livraria.model.Livro;
import com.iff.livraria.repository.Livros;
import com.iff.livraria.repository.Editoras;

@Controller
@RequestMapping("/livros")
public class LivrosController {

	@Autowired
	private Livros livros;
	@Autowired
	private Editoras editoras;

	@GetMapping()
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("ListaLivros");
		List<Livro> lista = livros.findAll();
		mv.addObject("livros",lista);
		mv.addObject("todasEditoras", editoras.findAll());
		mv.addObject(new Livro());
		return mv;
	}
	
	@PostMapping()
	public String salvar(Livro l, HttpServletRequest request) throws IOException{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file =  multipartRequest.getFile("file");
		l.setImage(file.getBytes());
		livros.save(l);
		return "redirect:/livros";
	}

	@RequestMapping(value = "/image/{image_id}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("image_id") Long imageId) throws IOException {
		byte[] imageContent = livros.findOne(imageId).getImage();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}


	//URL de chamada http://.../excluir/1234				
	@RequestMapping(value = "/excluir/{id}")
	public String excluirLivroByPathVariable(@PathVariable Long id, RedirectAttributes attributes)	{
		this.livros.delete(id);
		attributes.addFlashAttribute("mensagem", "Livro excluído com sucesso!");
		return "redirect:/livros";
	}

	//URL de chamada http://.../alterar/1234
	@RequestMapping(value = "/alterar/{id}")
	public ModelAndView alterar(@PathVariable Long id, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("ListaLivros");
		List <Livro> lista = livros.findAll();
		mv.addObject("livros",lista);
		Livro livro = livros.findOne(id);
		mv.addObject(livro);
		return mv;
	}

}
