package com.cristianhenrique.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cristianhenrique.cursomc.domain.Categoria;
import com.cristianhenrique.cursomc.dto.CategoriaDTO;
import com.cristianhenrique.cursomc.repositories.CategoriaRepository;
import com.cristianhenrique.cursomc.services.exceptions.DataIntegrityException;
import com.cristianhenrique.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	//anotação para instanciar automaticamente pelo spring
	@Autowired
	private CategoriaRepository repo;
	//pegar a categoria por código
	public Categoria find(Integer id) {
		//objeto contaniner que vai carregar o objeto que for passado do tipo <Categoria>
		//encapsular se objeto está encapsulado ou não. Java 8 para eliminar o NullPointerException
		Optional<Categoria> obj =  repo.findById(id);
		//retorna o objeto ou o nullo
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id
											+", Tipo: "+Categoria.class.getName()));
	}
	
	//Serviço de inserção de dados
	
	public Categoria insert (Categoria obj) {
		//garantir que é um novo objeto será salvo para não considerar uma atualização
		obj.setId(null);
		return repo.save(obj);
	}
	public Categoria update (Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	//padrão DTO Data Trasnferin object OBJETO DE TRANSFERENCIA DE DADOS
	public List<Categoria> findAll() {
		
		return repo.findAll();
		
	}
	
	//Esquema de paginação de todos os registros
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction ){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());   
	}
	
}
