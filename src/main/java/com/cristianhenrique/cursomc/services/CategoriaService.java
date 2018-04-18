package com.cristianhenrique.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristianhenrique.cursomc.domain.Categoria;
import com.cristianhenrique.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	//anotação para instanciar automaticamente pelo spring
	@Autowired
	private CategoriaRepository repo;
	//pegar a categoria por código
	public Categoria buscar(Integer id) {
		//objeto contaniner que vai carregar o objeto que for passado do tipo <Categoria>
		//encapsular se objeto está encapsulado ou não. Java 8 para eliminar o NullPointerException
		Optional<Categoria> obj =  repo.findById(id);
		//retorna o objeto ou o nullo
		return obj.orElse(null);
	}

}
