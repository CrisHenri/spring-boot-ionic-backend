package com.cristianhenrique.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristianhenrique.cursomc.domain.Cliente;
import com.cristianhenrique.cursomc.repositories.ClienteRepository;
import com.cristianhenrique.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	//anotação para instanciar automaticamente pelo spring
	@Autowired
	private ClienteRepository repo;
	//pegar a categoria por código
	public Cliente buscar(Integer id) {
		//objeto contaniner que vai carregar o objeto que for passado do tipo <Cliente>
		//encapsular se objeto está encapsulado ou não. Java 8 para eliminar o NullPointerException
		Optional<Cliente> obj =  repo.findById(id);
		//retorna o objeto ou o nullo
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id
											+", Tipo: "+Cliente.class.getName()));
	}

}
