package com.cristianhenrique.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cristianhenrique.cursomc.domain.Cliente;
import com.cristianhenrique.cursomc.domain.Cliente;
import com.cristianhenrique.cursomc.dto.ClienteDTO;
import com.cristianhenrique.cursomc.repositories.ClienteRepository;
import com.cristianhenrique.cursomc.services.exceptions.DataIntegrityException;
import com.cristianhenrique.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	//anotação para instanciar automaticamente pelo spring
	@Autowired
	private ClienteRepository repo;
	//pegar a categoria por código
	public Cliente find(Integer id) {
		//objeto contaniner que vai carregar o objeto que for passado do tipo <Cliente>
		//encapsular se objeto está encapsulado ou não. Java 8 para eliminar o NullPointerException
		Optional<Cliente> obj =  repo.findById(id);
		//retorna o objeto ou o nullo
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id
											+", Tipo: "+Cliente.class.getName()));
	}
	
	public Cliente update (Cliente obj) {
		Cliente  newObj = find(obj.getId());
		updateData(newObj, obj);
		
		return repo.save(newObj);
	}


	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	//padrão DTO Data Trasnferin object OBJETO DE TRANSFERENCIA DE DADOS
	public List<Cliente> findAll() {
		
		return repo.findAll();
		
	}
	
	//Esquema de paginação de todos os registros
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction ){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);    
	}
	

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
}
