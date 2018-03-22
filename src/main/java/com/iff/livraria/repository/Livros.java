package com.iff.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iff.livraria.model.Livro;

public interface Livros extends JpaRepository<Livro, Long> {
	
	
}